package de.atlasmc.plugin;

import java.io.File;
import java.util.List;

import de.atlasmc.atlasnetwork.server.ServerGroup;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.FunctionalListener;
import de.atlasmc.event.Listener;
import de.atlasmc.server.LocalServer;

public interface PluginManager {
	
	/**
	 * Loads and returns the Plugin represented by the file or null if unable to load.<br>
	 * The returned Plugin is not active. It is required to call {@link Plugin#load()} and {@link Plugin#enable()}
	 * @param file file of the Plugin
	 * @param enable if the Plugin should be enabled
	 * @param checkDependencies if dependencies should be checked before loading
	 * @return Plugin or null if not successful
	 */
	Plugin loadPlugin(File file, boolean enable, boolean checkDependencies);
	
	/**
	 * Unloads the given Plugin and removes it from this PluginManager
	 * @param plugin to unload
	 * @return true if success
	 */
	boolean unloadPlugin(Plugin plugin);
	
	default List<Plugin> loadPlugins(File directory) {
		return loadPlugins(directory, true, true);
	}
	
	/**
	 * Loads all Plugins. Should only be called on startup.
	 * @param directory of Plugin files
	 * @param enable if the Plugin should be enabled
	 * @param checkDependencies if dependencies should be checked before loading
	 * @return List of Plugins that are successfully loaded
	 * @see #loadPlugins(File)
	 */
	List<Plugin> loadPlugins(File directory, boolean enable, boolean checkDependencies);
	
	/**
	 * Unloads all Plugins and removes them from this PluginManager
	 * @param plugins to unload
	 * @return true if all plugins successfully unloaded
	 */
	boolean unloadPlugins(Plugin... plugins);
	
	List<Plugin> getPlugins();
	
	Plugin getPlugin(String name);
	
	List<PluginLoader> getLoaders();
	
	/**
	 * Adds a PluginLoader
	 * @param loader
	 * @return success
	 */
	boolean addLoader(PluginLoader loader);
	
	/**
	 * Removes a PluginLoader
	 * @param loader
	 * @return success
	 */
	boolean removeLoader(PluginLoader loader);

	/**
	 * Returns the number of Plugins registered on this PluginManager
	 * @return count
	 */
	int getPluginCount();
	
	void registerEvents(Plugin plugin, Listener listener);
	
	void registerEvents(Plugin plugin, Listener listener, ServerGroup group);
	
	void registerEvents(Plugin plugin, Listener listener, LocalServer server);
	
	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener);
	
	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, ServerGroup group);
	
	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, LocalServer server);

	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority);

	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, ServerGroup group, boolean ignoreCancelled, EventPriority priority);	

	<E extends Event> void registerFunctionalListener(Plugin plugin, Class<E> eventClass, FunctionalListener<E> listener, LocalServer server, boolean ignoreCancelled, EventPriority priority);
	
	void callEvent(Event event);
	
}
