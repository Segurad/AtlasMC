package de.atlasmc.core.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Dependency;
import de.atlasmc.plugin.Dependency.Type;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginException;
import de.atlasmc.plugin.PrototypePlugin;
import de.atlasmc.util.concurrent.future.CompletableFuture;

final class CoreLoadPluginTask implements Runnable {

	private final CorePluginManager manager;
	private final Log log;
	private final Map<File, CompletableFuture<Plugin>> fileFutures;
	private final Object pluginLock;
	private Map<String, CoreRegisteredPlugin> plugins;
	private List<CoreRegisteredPlugin> loadOrder;

	public CoreLoadPluginTask(CorePluginManager manager, Map<File, CompletableFuture<Plugin>> fileFutures, Object pluginLock) {
		this.manager = manager;
		this.fileFutures = fileFutures;
		this.pluginLock = pluginLock;
		log = manager.getLogger();
	}

	@Override
	public void run() {
		// prepare
		plugins = preparePlugins();
		if (plugins.isEmpty())
			return;
		// resolve dependencies
		loadOrder = resolveDependencies();
		if (loadOrder.isEmpty())
			return;
		// create
		createPlugins();
		if (loadOrder.isEmpty())
			return;
		// load
		loadPlugins();
		if (loadOrder.isEmpty())
			return;
		// enable
		enablePlugins();
		if (loadOrder.isEmpty())
			return;
		// register
		registerPlugins();
	}
	
	private void createPlugins() {
		for (int i = 0; i < loadOrder.size(); i++) {
			CoreRegisteredPlugin plugin = loadOrder.get(i);
			PrototypePlugin prototype = plugin.prototype;
			if (prototype.getPlugin() != null)
				continue;
			// TODO validate dependencies
			try {
				plugin.prototype.create();
				log.debug("Plugin created: " + prototype);
			} catch (Exception e) {
				loadOrder.remove(i--);
				if (plugins.get(prototype.getName()) == plugin) { // only full clear on loading plugins
					plugin.clearDependencies();
					plugin.clearDepending();
				}
				String msg = "Error while creating plugin: " + prototype;
				log.error(msg, e);
				fail(plugin, new PluginException(msg, e));
			}
		}
	}
	
	private void loadPlugins() {
		for (int i = 0; i < loadOrder.size(); i++) {
			CoreRegisteredPlugin plugin = loadOrder.get(i);
			PrototypePlugin prototype = plugin.prototype;
			Plugin pl = prototype.getPlugin();
			// TODO validate dependencies
			try {
				pl.load();
				log.debug("Plugin loaded: " + prototype);
			} catch (Exception e) {
				loadOrder.remove(i--);
				if (plugins.get(prototype.getName()) == plugin) { // only full clear on loading plugins
					plugin.clearDependencies();
					plugin.clearDepending();
					// TODO destroy plugin
				}
				String msg = "Error while loading plugin: " + prototype;
				log.error(msg, e);
				fail(plugin, new PluginException(msg, e));
			}
		}
	}
	
	private void enablePlugins() {
		for (int i = 0; i < loadOrder.size(); i++) {
			CoreRegisteredPlugin plugin = loadOrder.get(i);
			PrototypePlugin prototype = plugin.prototype;
			Plugin pl = prototype.getPlugin();
			if (pl.isEnabled())
				continue;
			// TODO validate dependencies
			try {
				pl.enable();
				log.debug("Plugin enabled: " + prototype);
			} catch (Exception e) {
				loadOrder.remove(i--);
				if (plugins.get(prototype.getName()) == plugin) { // only full clear on loading plugins
					plugin.clearDependencies();
					plugin.clearDepending();
					// TODO destroy plugin
				}
				String msg = "Error while enabled plugin: " + prototype;
				log.error(msg, e);
				fail(plugin, new PluginException(msg, e));
			}
		}
	}
	
	private void registerPlugins() {
		for (int i = 0; i < loadOrder.size(); i++) {
			CoreRegisteredPlugin plugin = loadOrder.get(i);
			if (pluginLock == null) {
				plugin.setKeepLoaded(true);
			} else {
				plugin.lock(pluginLock);
			}
			manager.internalRegisterPlugin(plugin);
		}
	}
	
	private Map<String, CoreRegisteredPlugin> preparePlugins() {
		Map<String, CoreRegisteredPlugin> plugins = new HashMap<>(fileFutures.size());
		for (Entry<File, CompletableFuture<Plugin>> entry : fileFutures.entrySet()) {
			final File file = entry.getKey();
			CoreRegisteredPlugin plugin = manager.getRegisteredPlugin(file);
			if (plugin != null) {
				plugins.put(plugin.prototype.getName(), plugin);
				continue;
			}
			PrototypePlugin prototype = manager.preparePlugin(file);
			if (prototype == null) {
				CompletableFuture<Plugin> future = entry.getValue();
				String msg = "Unable to load plugin (no loader?): " + file;
				log.error(msg);
				future.finish(null, new PluginException(msg));
				continue;
			}
			List<NamespacedKey> features = prototype.getRequiredFeatures();
			final int size = features.size();
			boolean satisfied = true;
			for (int i = 0; i < size; i++) {
				NamespacedKey feature = features.get(i);
				if (features.contains(feature)) {
					satisfied = false;
					CompletableFuture<Plugin> future = entry.getValue();
					String msg = "Required feature: " + feature + " is unsatisfied in plugin: " + prototype;
					log.error(msg);
					future.finish(null, new PluginException(msg));
					break;
				}
			}
			if (!satisfied)
				continue;
			List<NamespacedKey> softFeatures = prototype.getSoftRequiredFeatures();
			final int softsize = softFeatures.size();
			satisfied = false;
			for (int i = 0; i < softsize; i++) {
				NamespacedKey feature = softFeatures.get(i);
				if (features.contains(feature)) {
					satisfied = true;
					break;
				}
			}
			if (!satisfied) {
				CompletableFuture<Plugin> future = entry.getValue();
				String msg = "Some soft required features are unsatisfied in plugin: " + prototype;
				log.error(msg);
				future.finish(null, new PluginException(msg));
				continue;
			}
			plugins.put(prototype.getName(), new CoreRegisteredPlugin(manager, prototype, null));
		}
		return plugins;
	}
	
	private List<CoreRegisteredPlugin> resolveDependencies() {
		List<CoreRegisteredPlugin> resolved = new ArrayList<>(plugins.size());
		Map<String, CoreRegisteredPlugin> unresolved = new HashMap<>(plugins);
		for (CoreRegisteredPlugin plugin : plugins.values()) {
			resolvePluginDependencies(plugin, resolved, unresolved);
		}
		return resolved;
	}
	
	private boolean resolvePluginDependencies(CoreRegisteredPlugin plugin, List<CoreRegisteredPlugin> resolved, Map<String, CoreRegisteredPlugin> unresolved) {
		if (!unresolved.remove(plugin.prototype.getName(), plugin)) {
			return resolved.contains(plugin); // already resolved or failed
		}
		List<Dependency> dependencies = plugin.prototype.getDependencies();
		if (dependencies.isEmpty()) {
			resolved.add(plugin);
			return true;
		}
		final int size = dependencies.size();
		for (int i = 0; i < size; i++) {
			Dependency dependency = dependencies.get(i);
			CoreRegisteredPlugin dependencyPlugin = plugins.get(dependency.name);
			if (dependencyPlugin == null) {
				manager.getRegisteredPlugin(dependency.name);
			}
			if (dependencyPlugin == null) {
				if (dependency.type != Type.REQUIRED)
					continue;
				String msg = "Missing dependency: " + dependency + " for plugin: " + plugin.prototype;
				log.error(msg);
				fail(plugin, new PluginException(msg));
				return false;
			}
			if (dependency.matches(dependencyPlugin.prototype.getVersion())) {
				if (dependency.type == Type.INCOMPATIPLE) {
					String msg = "Incompatiple Plugin found: " + dependencyPlugin.prototype + " for plugin: " + plugin.prototype;
					log.error(msg);
					fail(plugin, new PluginException(msg));
					return false;
				}
				switch (dependency.order) {
				case AFTER:
					if (!resolvePluginDependencies(dependencyPlugin, resolved, unresolved) && dependency.type == Type.REQUIRED) {
						String msg = "Dependency was not resolved: " + dependencyPlugin.prototype + " for plugin: " + plugin.prototype;
						log.error(msg);
						fail(plugin, new PluginException(msg));
						return false;	
					}
					resolved.add(plugin);
					plugin.addDependency(plugin);
					break;
				case BEFORE:
					if (!resolvePluginDependencies(dependencyPlugin, resolved, unresolved)) {
						if (dependency.type == Type.REQUIRED) {
							String msg = "Dependency was not resolved: " + dependencyPlugin + " for plugin: " + plugin.prototype;
							log.error(msg);
							fail(plugin, new PluginException(msg));
							return false;
						}
						resolved.add(plugin);
					} else {
						int index = resolved.indexOf(dependencyPlugin);
						if (index == -1) { // should never happen just in case
							String msg = "Dependency was not resolved: " + dependencyPlugin.prototype.getName() + " " + dependencyPlugin.prototype.getVersion() + " for plugin: " + plugin.prototype;
							log.error(msg);
							fail(plugin, new PluginException(msg));
							return false;
						}
						resolved.add(index, plugin);
						dependencyPlugin.addDependency(plugin);
					}
					break;
				case WHATEVER:
					resolved.add(plugin);
					plugin.addDependency(dependencyPlugin);
					break;
				default:
					String msg = "Unable to resolve dependency order order (" + dependency.order + " | " + dependency + ") for plugin: " + plugin.prototype;
					log.error(msg);
					fail(plugin, new PluginException(msg));
					return false;
				}
			} else if (dependency.type == Type.REQUIRED){
				String msg = "Unsatisfied dependency " + dependency + " for plugin: " + plugin.prototype;
				log.error(msg);
				fail(plugin, new PluginException(msg));
				return false;
			}
		}
		return true;
	}
	
	private void fail(CoreRegisteredPlugin plugin, Exception cause) {
		CompletableFuture<Plugin> future = fileFutures.get(plugin.prototype.getFile());
		if (future != null && !future.isDone())
			future.finish(null, cause);
	}

}
