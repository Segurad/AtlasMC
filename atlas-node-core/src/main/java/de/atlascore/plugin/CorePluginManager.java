package de.atlascore.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.FunctionalListener;
import de.atlasmc.event.FunctionalListenerExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.plugin.PreparedPlugin;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;

public class CorePluginManager implements PluginManager {
	
	public static final Plugin SYSTEM = new AtlasSystemPlugin();
	
	private final Log logger;
	private final Map<String, Plugin> plugins;
	private final List<PluginLoader> loaders;
	private final Map<String, Future<Plugin>> loading;
	private final Lock lock;
	
	public CorePluginManager(Log logger) {
		this.logger = logger;
		this.lock = new ReentrantLock();
		this.plugins = new ConcurrentHashMap<>();
		this.loading = new ConcurrentHashMap<>();
		this.loaders = new CopyOnWriteArrayList<>();
	}

	@Override
	public boolean unloadPlugin(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		try {
			plugin.disable();
		} catch(Exception e) {
			logger.error("Error while disabling plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
			return false;
		}
		try {
			plugin.unload();
		} catch(Exception e) {
			logger.error("Error while unloading plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
			return false;
		}
		try {
			plugin.getPluginLoader().remove(plugin.getClass().getClassLoader());
		} catch (Exception e) {
			logger.error("Error while removing classloader of plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
			return false;
		}
		try {
			HandlerList.unregisterListenerGloabal(plugin);
		} catch(Exception e) {
			logger.error("Error while removing listeners of plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
		}
		try {
			Atlas.getScheduler().removeTasks(plugin);
		} catch(Exception e) {
			logger.error("Error while removing tasks of plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
		}
		lock.lock();
		plugins.remove(plugin.getName(), plugin);
		lock.unlock();
		return true;
	}
	
	@Override
	public Future<Plugin> loadPlugin(File file, boolean enable, boolean checkDependencies) {
		return internalLoadPlugin(file, enable, checkDependencies, false);
	}
	
	@Override
	public List<Future<Plugin>> loadPlugins(Collection<File> files, boolean enable, boolean checkDependencies) {
		return internalLoadPlugins(files, enable, checkDependencies, false);
	}
	
	private Future<Plugin> internalLoadPlugin(File file, boolean enable, boolean checkDependencies, boolean async) {
		if (file.exists())
			throw new IllegalArgumentException("File does not exist: " + file.getPath());
		List<Future<Plugin>> futures = internalLoadPlugins(List.of(file), enable, checkDependencies, async);
		return futures.get(0);
	}
	
	private List<Future<Plugin>> internalLoadPlugins(File directory, boolean enable, boolean checkDependencies, boolean async) {
		if (directory == null)
			throw new IllegalArgumentException("Directory can not be null!");
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Directory does not exist: ".concat(directory.getPath()));
		logger.debug("Loading plugins from directory: {}", directory.getPath());
		return internalLoadPlugins(List.of(directory.listFiles()), enable, checkDependencies, async);
	}
	
	private List<Future<Plugin>> internalLoadPlugins(Collection<File> files, boolean enable, boolean checkDependencies, boolean async) {
		lock.lock();
		List<Future<Plugin>> futures = new ArrayList<>(files.size());
		Map<String, CorePluginLoaderTask> prepared = null;
		Map<String, Future<Plugin>> loadingFuture = new HashMap<>(files.size());
		for (File file : files) {
			// prepare plugin
			PreparedPlugin plugin = preparePlugin(file);
			if (plugin == null) {
				futures.add(new CompleteFuture<>(new PluginException("Unable to load plugin (no loader?): " + file)));
				continue;
			}
			final String pluginName = plugin.getName();
			// check if loading
			Future<Plugin> future = loading.get(pluginName);
			if (future != null) {
				loadingFuture.put(pluginName, future);
				futures.add(future);
				continue;	
			}
			// check if loaded
			Plugin p = plugins.get(pluginName);
			if (p != null) {
				Future<Plugin> f = CompleteFuture.of(p);
				loadingFuture.put(pluginName, f);
				futures.add(f);
				continue;
			}
			// add for loading
			logger.debug("Prepared plugin: {}", pluginName);
			if (prepared == null)
				prepared = new HashMap<>();
			CorePluginLoaderTask task = new CorePluginLoaderTask(this, plugin, enable, async);
			this.loading.put(pluginName, task.getFuture());
			prepared.put(pluginName, task);
			loadingFuture.put(pluginName, task.getFuture());
		}
		lock.unlock();
		if (prepared == null) {
			// all plugins loaded or not able to load
			return futures.isEmpty() ? List.of() : futures;
		}
		// try check dependencies for plugins to load
		if (checkDependencies) {
			logger.debug("Resolving dependencies...");
			for (CorePluginLoaderTask task : prepared.values()) {
				// check load before
				PreparedPlugin preped = task.getPlugin();
				List<String> loadbefore = preped.getPluginInfo().getStringList("load-before");
				if (loadbefore == null || loadbefore.isEmpty())
					continue;
				for (String name : loadbefore) {
					CorePluginLoaderTask after = prepared.get(name);
					if (after == null)
						continue;
					after.getPlugin().addSoftDependency(task.getFuture());
				}
				// resolve soft depends and depends on
				resolveDependencies(preped, loadingFuture);
			}
		}
		for (CorePluginLoaderTask preped : prepared.values()) {
			preped.prepare();
		}
		logger.debug("Loading plugins...");
		if (async) {
			for (CorePluginLoaderTask preped : prepared.values()) {
				if (preped.isReady() && !preped.isFinished()) {
					preped.schedule();
				}
			}
		} else {
			int loaded = 0;
			do {
				loaded = 0;
				for (CorePluginLoaderTask preped : prepared.values()) {
					if (preped.isReady() && !preped.isFinished()) {
						preped.run();
						loaded++;
					}
				}
			} while (loaded > 0);
		}
		return futures;
	}
	
	@Override
	public List<Future<Plugin>> loadPlugins(File directory, boolean enable, boolean checkDependencies) {
		if (directory == null)
			throw new IllegalArgumentException("Directory can not be null!");
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Directory does not exist: ".concat(directory.getPath()));
		logger.debug("Loading plugins from directory: {}", directory.getPath());
		return loadPlugins(List.of(directory.listFiles()), enable, checkDependencies);
	}
	
	private void resolveDependencies(PreparedPlugin plugin, Map<String, Future<Plugin>> plugins) {
		List<String> dependencies = plugin.getPluginInfo().getStringList("depends-on");
		boolean missingDependencies = false;
		for (String dependency : dependencies) {
			Future<Plugin> future = plugins.get(dependency);
			if (future != null) {
				plugin.addDependency(future);
				continue;
			}
			if (getPlugin(dependency) != null)
				continue;
			logger.warn("Missing dependency \"{}\" for plugin: {}", dependency, plugin.getName());
			missingDependencies = true;
		}
		if (missingDependencies) {
			logger.error("Unable to load plugin: {} (Missing dependencies)!", plugin.getName());
			plugin.setInvalid();
			return;
		}
		dependencies = plugin.getPluginInfo().getStringList("soft-depends-on");
		for (String dependency : dependencies) {
			Future<Plugin> future = plugins.get(dependency);
			if (future != null) {
				plugin.addSoftDependency(future);
				continue;
			}
			if (getPlugin(dependency) != null)
				continue;
			logger.debug("Missing soft dependency \"{}\" for plugin: {}", dependency, plugin.getName());
		}
	}
	
	/**
	 * Loads the source of the PreparedPlugin, it's dependencies, loads and enable the Plugin.
	 * Returns null if failed to load the PreparedPlugin
	 * @param prepared
	 * @param enable
	 * @return plugin
	 * @throws PluginException
	 */
	Plugin loadPreparedPlugin(CorePluginLoaderTask task, boolean enable) {
		PreparedPlugin prepared = task.getPlugin();
		if (prepared.isInvalid())
			return null;
		if (prepared.isLoaded())
			return prepared.getPlugin();
		if (prepared.hasDependencies()) {
			boolean missingDependencies = false;
			for (Future<Plugin> dependency : prepared.getDependencies()) {
				if (!dependency.isSuccess()) {
					missingDependencies = true;
					break;
				}
			}
			if (missingDependencies) {
				throw new PluginException("Unable to load plugin: " + prepared.getName() + " (Missing dependencies)!");
			}
		}
		Plugin plugin = null;
		try {
			plugin = prepared.load();
		} catch (Exception e) {
			prepared.setInvalid();
			throw new PluginException("Error while loading plugin source: " + prepared.getName(), e);
		}
		if (plugin.isEnabled())
			// plugin already loaded
			return plugin;
		try {
			plugin.load();
		} catch(Exception e) {
			throw new PluginException("Error while loading plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
		}
		if (!enable) {
			registerPlugin(plugin);
			return plugin;
		}
		try {
			plugin.enable();
		} catch(Exception e) {
			throw new PluginException("Error while enabling plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
		}
		registerPlugin(plugin);
		return plugin;
	}
	
	private void registerPlugin(Plugin plugin) {
		lock.lock();
		plugins.put(plugin.getName(), plugin);
		loading.remove(plugin.getName());
		lock.unlock();
	}
	
	/**
	 * Returns the file as PreparedPlugin or null if unable to load or already loaded
	 * @param file
	 * @return PreparedPlugin or null
	 */
	private PreparedPlugin preparePlugin(File file) {
		try {
			for (PluginLoader loader : loaders) {
				PreparedPlugin plugin = loader.preparePlugin(file);
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
	public boolean unloadPlugins(Plugin... plugins) {
		boolean success = true;
		for (Plugin plugin : plugins) {
			if (!unloadPlugin(plugin))
				success = false;
		}
		return success;
	}

	@Override
	public List<Plugin> getPlugins() {
		return List.copyOf(plugins.values());
	}
	
	@Override
	public int getPluginCount() {
		return plugins.size();
	}

	@Override
	public Plugin getPlugin(String name) {
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		return plugins.get(name);
	}

	@Override
	public List<PluginLoader> getLoaders() {
		return List.copyOf(loaders);
	}

	@Override
	public boolean addLoader(PluginLoader loader) {
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
	public void registerEvents(Plugin plugin, Listener listener) {
		HandlerList.registerListener(plugin, listener);
	}

	@Override
	public void registerEvents(Plugin plugin, Listener listener, Object... context) {
		HandlerList.registerListener(plugin, listener, context);
	}
	
	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener) {
		HandlerList.registerFunctionalListener(plugin, eventClass, listener);
	}

	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, Object... context) {
		HandlerList.registerFunctionalListener(plugin, eventClass, listener, context);
	}
	
	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass,
			FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority) {
		HandlerList handlers = HandlerList.getHandlerListOf(eventClass);
		FunctionalListenerExecutor exe = new FunctionalListenerExecutor(plugin, eventClass, listener, priority, ignoreCancelled);
		handlers.registerExecutor(exe);
	}

	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass,
			FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority, Object... context) {
		HandlerList handlers = HandlerList.getHandlerListOf(eventClass);
		FunctionalListenerExecutor exe = new FunctionalListenerExecutor(plugin, eventClass, listener, priority, ignoreCancelled);
		handlers.registerExecutor(exe, context);
	}

	@Override
	public void callEvent(Event event) {
		HandlerList.callEvent(event);
	}
	
	private static class AtlasSystemPlugin implements Plugin {

		@Override
		public void load() {}

		@Override
		public void enable() {}

		@Override
		public void disable() {}

		@Override
		public void unload() {}

		@Override
		public void reload() {}

		@Override
		public void loadConfiguration(PluginConfiguration config) {}

		@Override
		public void unloadConfiguration(NamespacedKey config) {}

		@Override
		public void reloadConfiguration(NamespacedKey config) {}

		@Override
		public void reloadConfigurations() {}

		@Override
		public String getVersion() {
			return Atlas.FULL_VERSION;
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
		
	}

	@Override
	public Future<Plugin> loadPluginAsync(File file, boolean enable, boolean checkDependencies) {
		return internalLoadPlugin(file, enable, checkDependencies, true);
	}

	@Override
	public List<Future<Plugin>> loadPluginsAsync(File directory, boolean enable, boolean checkDependencies) {
		return internalLoadPlugins(directory, enable, checkDependencies, true);
	}

	@Override
	public List<Future<Plugin>> loadPluginsAsync(Collection<File> files, boolean enable, boolean checkDependencies) {
		return internalLoadPlugins(files, enable, checkDependencies, true);
	}
	
}
