package de.atlascore.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.FunctionalListener;
import de.atlasmc.event.FunctionalListenerExecutor;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.Listener;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginConfiguration;
import de.atlasmc.plugin.PluginLoader;
import de.atlasmc.plugin.PluginManager;
import de.atlasmc.plugin.PreparedPlugin;

public class CorePluginManager implements PluginManager {
	
	public static final Plugin SYSTEM = new AtlasSystemPlugin();
	
	private final Log logger;
	private final Map<String, Plugin> plugins;
	private final List<PluginLoader> loaders;
	
	public CorePluginManager(Log logger) {
		this.logger = logger;
		this.plugins = new ConcurrentHashMap<>();
		this.loaders = new CopyOnWriteArrayList<>();
	}
	
	@Override
	public Plugin loadPlugin(File file, boolean enable, boolean checkDependencies) {
		if (file.exists())
			throw new IllegalArgumentException("File does not exist: ".concat(file.getPath()));
		PreparedPlugin prepared = preparePlugin(file);
		boolean missingDependencies = false;
		if (checkDependencies) {
			List<String> dependencies = prepared.getPluginInfo().getStringList("depends-on");
			if (dependencies != null) {
				for (String dependency : dependencies) {
					if (getPlugin(dependency) == null) {
						logger.error("Missing dependency \"{}\" for plugin: {}", dependency, prepared.getName());
						missingDependencies = true;
					}
				}
			}
		}
		if (missingDependencies) {
			logger.error("Unable to load plugin: {} (Missing dependencies)!", prepared.getName());
			return null;
		}
		return loadPreparedPlugin(prepared, enable);
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
		plugins.remove(plugin.getName(), plugin);
		return true;
	}
	
	@Override
	public List<Plugin> loadPlugins(File directory, boolean enable, boolean checkDependencies) {
		if (directory == null)
			throw new IllegalArgumentException("Directory can not be null!");
		if (!directory.isDirectory())
			throw new IllegalArgumentException("Directory does not exist: ".concat(directory.getPath()));
		logger.debug("Loading plugins from directory: {}", directory.getPath());
		Map<String, PreparedPlugin> prepared = null;
		for (File file : directory.listFiles()) {
			if (!file.isFile())
				continue;
			PreparedPlugin plugin = preparePlugin(file);
			if (plugin == null)
				continue;
			logger.debug("Prepared plugin: {}", plugin.getName());
			if (prepared == null)
				prepared = new HashMap<>();
			prepared.put(plugin.getName(), plugin);
		}
		if (prepared == null)
			return List.of();
		if (checkDependencies) {
			logger.debug("Resolving dependencies...");
			for (PreparedPlugin preped : prepared.values()) {
				// check load before
				List<String> loadbefore = preped.getPluginInfo().getStringList("load-before");
				if (loadbefore == null)
					continue;
				for (String name : loadbefore) {
					PreparedPlugin after = prepared.get(name);
					if (after == null)
						continue;
					Set<PreparedPlugin> dependencies = after.getDependencies();
					dependencies.add(preped);
				}
				// resolve soft depends and depends on
				resolveDependencies(preped, prepared);
			}
		}
		List<Plugin> list = null;
		logger.debug("Loading plugins...");
		for (PreparedPlugin preped : prepared.values()) {
			Plugin plugin = loadPreparedPlugin(preped, enable);
			if (plugin == null)
				continue;
			if (list == null)
				list = new ArrayList<>(prepared.size());
			list.add(plugin);
		}
		return list == null ? List.of() : list;
	}
	
	private void resolveDependencies(PreparedPlugin plugin, Map<String, PreparedPlugin> plugins) {
		List<String> dependencies = plugin.getPluginInfo().getStringList("depends-on");
		boolean missingDependencies = false;
		for (String dependency : dependencies) {
			PreparedPlugin prepared = plugins.get(dependency);
			if (prepared != null) {
				plugin.getDependencies().add(prepared);
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
			PreparedPlugin prepared = plugins.get(dependency);
			if (prepared != null) {
				plugin.getDependencies().add(prepared);
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
	 * @return Plugin or null
	 */
	private Plugin loadPreparedPlugin(PreparedPlugin prepared, boolean enable) {
		if (prepared.isInvalid())
			return null;
		if (prepared.isLoaded())
			return prepared.getPlugin();
		if (prepared.hasDependencies()) {
			boolean missingDependencies = false;
			for (PreparedPlugin dependency : prepared.getDependencies()) {
				loadPreparedPlugin(dependency, enable);
				if (dependency.isInvalid()) {
					logger.warn("Missing dependency \"{}\" for plugin: {}", dependency.getName(), prepared.getName());
					missingDependencies = true;
				}
			}
			if (missingDependencies) {
				logger.error("Unable to load plugin: {} (Missing dependencies)!", prepared.getName());
				return null;
			}
		}
		if (prepared.hasSoftDependencies()) {
			for (PreparedPlugin dependency : prepared.getSoftDependencies()) {
				loadPreparedPlugin(dependency, enable);
				if (dependency.isInvalid()) {
					logger.debug("Missing soft dependency \"{}\" for plugin: {}", dependency.getName(), prepared.getName());
				}
			}
		}
		Plugin plugin = null;
		try {
			plugin = prepared.load();
		} catch (Exception e) {
			logger.error("Error while loading plugin source: " + prepared.getName(), e);
			prepared.setInvalid();
			return null;
		}
		if (plugin.isEnabled())
			return plugin;
		try {
			plugin.load();
		} catch(Exception e) {
			logger.error("Error while loading plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
			return plugin;
		}
		if (!enable)
			return plugin;
		try {
			plugin.enable();
		} catch(Exception e) {
			logger.error("Error while enabling plugin: " + plugin.getName() + " v" + plugin.getVersion(), e);
			return plugin;
		}
		return plugin;
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
				if (plugins.containsKey(plugin.getName())) {
					logger.error("Plugin with name {} already loaded.", plugin.getName());
					return null;
				}
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
	public void registerEvents(Plugin plugin, Listener listener, ServerGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Group can not be null!");
		HandlerList.registerListener(plugin, listener, group);
	}

	@Override
	public void registerEvents(Plugin plugin, Listener listener, LocalServer server) {
		if (server == null)
			throw new IllegalArgumentException("Server can not be null!");
		HandlerList.registerListener(plugin, listener, server);
	}
	
	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener) {
		HandlerList.registerFunctionalListener(plugin, eventClass, listener);
	}

	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, ServerGroup group) {
		if (group == null)
			throw new IllegalArgumentException("Group can not be null!");
		HandlerList.registerFunctionalListener(plugin, eventClass, listener, group);
	}

	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, LocalServer server) {
		if (server == null)
			throw new IllegalArgumentException("Server can not be null!");
		HandlerList.registerFunctionalListener(plugin, eventClass, listener, server);
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
			FunctionalListener<E> listener, ServerGroup group, boolean ignoreCancelled, EventPriority priority) {
		if (group == null)
			throw new IllegalArgumentException("Group can not be null!");
		HandlerList handlers = HandlerList.getHandlerListOf(eventClass);
		FunctionalListenerExecutor exe = new FunctionalListenerExecutor(plugin, eventClass, listener, priority, ignoreCancelled);
		handlers.registerExecutor(exe, group);
	}

	@Override
	public <E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass,
			FunctionalListener<E> listener, LocalServer server, boolean ignoreCancelled, EventPriority priority) {
		if (server == null)
			throw new IllegalArgumentException("Server can not be null!");
		HandlerList.registerListener(plugin, listener, server);
		HandlerList handlers = HandlerList.getHandlerListOf(eventClass);
		FunctionalListenerExecutor exe = new FunctionalListenerExecutor(plugin, eventClass, listener, priority, ignoreCancelled);
		handlers.registerExecutor(exe, server);
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
			return "Atlas System";
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
	
}
