package de.atlasmc.core.plugin;

import java.io.File;
import java.io.InputStream;
import java.lang.ref.ReferenceQueue;
import java.net.URL;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.plugin.PrototypePlugin;
import de.atlasmc.plugin.Version;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.iterator.UnboxingIterator;

public class CorePluginManager implements PluginManager {
	
	public static final Plugin SYSTEM = new AtlasSystemPlugin();
	
	final ReferenceQueue<Object> lockQueue;
	private final Log logger;
	private final Map<String, CoreRegisteredPlugin> plugins;
	private final List<PluginLoader> loaders;
	private final Set<NamespacedKey> features;
	private final Values values;
	private final CorePluginManagerWorker worker;
	
	public CorePluginManager(Log logger) {
		this.logger = Objects.requireNonNull(logger, "logger");
		this.plugins = new ConcurrentHashMap<>();
		this.loaders = new CopyOnWriteArrayList<>();
		this.lockQueue = new ReferenceQueue<>();
		this.features = ConcurrentHashMap.newKeySet();
		this.values = new Values();
		new CorePluginLockMonitor(lockQueue, logger).start();
		this.worker = new CorePluginManagerWorker(logger);
		worker.start();
	}

	@Override
	public Future<Boolean> unloadPlugin(Plugin plugin, boolean force) {
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return CompleteFuture.getBooleanFuture(true);
		CompletableFuture<Boolean> future = new CompletableFuture<>();
		worker.queueTask(new CoreUnloadPluginTask(this, future, registered, force));
		return future;
	}
	
	@Override
	public Future<Plugin> loadPlugin(File file, Object lock) {
		if (file.exists())
			throw new IllegalArgumentException("File does not exist: " + file.getPath());
		List<Future<Plugin>> futures = internalLoadPlugins(List.of(file), lock);
		if (futures.isEmpty())
			return CompleteFuture.getNullFuture();
		return futures.get(0);
	}
	
	@Override
	public Future<Plugin> loadRepoPlugin(NamespacedKey entry, Object lock) {
		Objects.requireNonNull(entry, "entry");
		List<Future<Plugin>> futures = internalLoadRepoPlugins(List.of(entry), lock);
		if (futures.isEmpty())
			return CompleteFuture.getNullFuture();
		return futures.get(0);
	}
	
	@Override
	public List<Future<Plugin>> loadPlugins(Collection<File> files, Object lock) {
		return internalLoadPlugins(files, lock);
	}
	
	@Override
	public List<Future<Plugin>> loadPlugins(File directory, Object lock) {
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Directory does not exist: ".concat(directory.getPath()));
		logger.debug("Loading plugins from directory: {}", directory.getPath());
		return internalLoadPlugins(List.of(directory.listFiles()), lock);
	}
	
	@Override
	public List<Future<Plugin>> loadRepoPlugins(Collection<NamespacedKey> entries, Object lock) {
		if (entries == null)
			throw new IllegalArgumentException("Entries can not be null!");
		return internalLoadRepoPlugins(entries, lock);
	}
	
	private List<Future<Plugin>> internalLoadPlugins(Collection<File> files, Object pluginLock) {
		List<Future<Plugin>> futures = new ArrayList<>(files.size());
		final Map<File, CompletableFuture<Plugin>> keyedFutures = new HashMap<>(futures.size());
		for (File file : files) {
			CompletableFuture<Plugin> future = new CompletableFuture<>();
			futures.add(future);
			keyedFutures.put(file, future);
		}
		worker.queueTask(new CoreLoadPluginTask(this, keyedFutures, pluginLock));
		return futures;
	}
	
	private List<Future<Plugin>> internalLoadRepoPlugins(Collection<NamespacedKey> entries, Object pluginLock) {
		List<Future<Plugin>> futures = new ArrayList<>(entries.size());
		final Map<NamespacedKey, CompletableFuture<Plugin>> keyedFutures = new HashMap<>(futures.size());
		for (NamespacedKey entry : entries) {
			CompletableFuture<Plugin> future = new CompletableFuture<>();
			futures.add(future);
			keyedFutures.put(entry, future);
		}
		worker.queueTask(new CoreLoadRepoPluginTask(this, keyedFutures, pluginLock));
		return futures;
	}
	
	void internalRegisterPlugin(CoreRegisteredPlugin plugin) {
		plugins.put(plugin.prototype.getName(), plugin);
	}
	
	/**
	 * Returns the file as PreparedPlugin or null if unable to load or already loaded
	 * @param file
	 * @return PreparedPlugin or null
	 */
	@Nullable
	PrototypePlugin preparePlugin(File file) {
		try {
			for (PluginLoader loader : loaders) {
				PrototypePlugin plugin = loader.preparePlugin(file);
				if (plugin == null) 
					continue;
				return plugin;
			}
		} catch (Exception e) {
			logger.error("Error while preparing plugin: " + file.getPath(), e);
		}
		return null;
	}

	@Override
	public Collection<Plugin> getPlugins() {
		return values;
	}
	
	@Override
	public int getPluginCount() {
		return plugins.size();
	}

	@Override
	public Plugin getPlugin(String name) {
		Objects.requireNonNull(name, "name");
		CoreRegisteredPlugin plugin = plugins.get(name);
		return plugin != null ? plugin.prototype.getPlugin() : null;
	}

	@Override
	public List<PluginLoader> getLoaders() {
		return List.copyOf(loaders);
	}

	@Override
	public synchronized boolean addLoader(PluginLoader loader) {
		if (loaders.contains(loader)) 
			return false;
		loaders.add(loader);
		return true;
	}

	@Override
	public boolean removeLoader(PluginLoader loader) {
		return loaders.remove(loader);
	}

	@Override
	public Set<NamespacedKey> getFeatures() {
		return features;
	}

	@Override
	public boolean hasFeature(NamespacedKey feature) {
		return features.contains(feature);
	}

	@Override
	public void addFeature(NamespacedKey feature) {
		features.add(feature);
	}

	@Override
	public boolean removeFeature(NamespacedKey feature) {
		return features.remove(feature);
	}

	@Override
	public boolean lockPlugin(Plugin plugin, Object lock) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (lock == null)
			throw new IllegalArgumentException("Lock can not be null!");
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return false;
		return registered.lock(lock);
	}

	@Override
	public boolean unlockPlugin(Plugin plugin, Object lock) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (lock == null)
			throw new IllegalArgumentException("Lock can not be null!");
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return false;
		return registered.unlock(lock);
	}

	@Override
	public boolean isLocked(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return false;
		return !registered.isLocked();
	}

	@Override
	public int clearLocks(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return 0;
		return registered.clearLocks();
	}

	@Override
	public int lockCount(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		CoreRegisteredPlugin registered = plugins.get(plugin.getName());
		if (registered == null)
			return 0;
		return registered.lockCount();
	}
	
	@Override
	public boolean isKeepLoaded(Plugin plugin) {
		CoreRegisteredPlugin pl = plugins.get(plugin.getName());
		return pl.isKeepLoaded();
	}
	
	@Override
	public void setKeepLoaded(Plugin plugin, boolean keepLoaded) {
		CoreRegisteredPlugin pl = plugins.get(plugin.getName());
		pl.setKeepLoaded(keepLoaded);
	}

	Log getLogger() {
		return logger;		
	}
	
	@Nullable
	CoreRegisteredPlugin getRegisteredPlugin(File file) {
		for (var plugin : plugins.values()) {
			if (plugin.prototype.getFile().equals(file));
				return plugin;
		}
		return null;
	}
	
	@Nullable
	CoreRegisteredPlugin getRegisteredPlugin(String name) {
		return plugins.get(name);
	}
	
	void removePlugin(CoreRegisteredPlugin plugin) {
		plugins.remove(plugin.prototype.getName(), plugin);
	}

	void tryUnloadPlugin(CoreRegisteredPlugin plugin) {
		if (plugins.get(plugin.prototype.getName()) != plugin)
			return;
		worker.queueTask(new CoreUnloadPluginTask(this, new CompletableFuture<>(), plugin, false));
	}
	
	private static class AtlasSystemPlugin implements Plugin {

		private final Version version = new Version("v0.0.0-dev");
		
		@Override
		public void load() {
			// not required
		}

		@Override
		public void enable() {
			// not required
		}

		@Override
		public void disable() {
			// not required
		}

		@Override
		public void unload() {
			// not required
		}

		@Override
		public void reload() {
			// not required
		}

		@Override
		public void loadConfiguration(PluginConfiguration config) {
			// not required
		}

		@Override
		public void unloadConfiguration(NamespacedKey config) {
			// not required
		}

		@Override
		public void reloadConfiguration(NamespacedKey config) {
			// not required
		}

		@Override
		public void reloadConfigurations() {
			// not required
		}

		@Override
		public Version getVersion() {
			return version;
		}

		@Override
		public List<String> getAuthor() {
			return List.of();
		}

		@Override
		public String getName() {
			return "Atlas-System";
		}

		@Override
		public String getDescription() {
			return "Atlas system internals";
		}

		@Override
		public boolean isLoaded() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

		@Override
		public PluginLoader getPluginLoader() {
			return null;
		}

		@Override
		public File getFile() {
			return null;
		}

		@Override
		public Log getLogger() {
			return Atlas.getLogger();
		}

		@Override
		public Plugin getPlugin() {
			return this;
		}

		@Override
		public Collection<PluginHandle> getHandles() {
			return List.of();
		}

		@Override
		public InputStream getResourceAsStream(String name) {
			return getClass().getResourceAsStream(name);
		}

		@Override
		public URL getResource(String name) {
			return getClass().getResource(name);
		}

		@Override
		public void loadConfiguration(PluginConfiguration config, Object context) {
			// not required
		}

		@Override
		public void unloadConfiguration(NamespacedKey config, Object context) {
			// not required
		}

		@Override
		public Collection<PluginConfiguration> getConfigurations() {
			return List.of();
		}

		@Override
		public PrototypePlugin getPrototype() {
			return null;
		}
		
	}
	
	private final class Values extends AbstractCollection<Plugin> {
		
		private static final Function<CoreRegisteredPlugin, Plugin> UNBOX = (u) -> {
			return u.prototype.getPlugin();
		};

		@Override
		public Iterator<Plugin> iterator() {
			return new UnboxingIterator<>(plugins.values().iterator(), UNBOX, false);
		}

		@Override
		public int size() {
			return plugins.size();
		}

		@Override
		public boolean contains(Object o) {
			if (o instanceof Plugin plugin) {
				CoreRegisteredPlugin registered = plugins.get(plugin.getName());
				if (registered == null)
					return false;
				if (registered.prototype.getPlugin() == plugin)
					return true;
			}
			return false;
		}

		@Override
		public boolean add(Plugin e) {
			return false;
		}

		@Override
		public boolean remove(Object o) {
			return false;
		}

		@Override
		public boolean addAll(Collection<? extends Plugin> c) {
			return false;
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return false;
		}

		@Override
		public void clear() {
			// not required
		}
		
	}
	
}
