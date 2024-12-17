package de.atlasmc.plugin;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.event.Event;
import de.atlasmc.event.EventPriority;
import de.atlasmc.event.FunctionalListener;
import de.atlasmc.event.Listener;
import de.atlasmc.util.concurrent.future.Future;

public interface PluginManager {
	
	/**
	 * Loads and returns the Plugin represented by the file or null if unable to load.<br>
	 * The returned Plugin is not active. It is required to call {@link Plugin#load()} and {@link Plugin#enable()}
	 * @param file file of the Plugin
	 * @param enable if the Plugin should be enabled
	 * @return Plugin or null if not successful
	 */
	Future<Plugin> loadPlugin(File file, boolean enable);
	
	default List<Future<Plugin>> loadPlugins(File directory) {
		return loadPlugins(directory, true);
	}
	
	/**
	 * Loads all Plugins. Should only be called on startup.
	 * @param directory of Plugin files
	 * @param enable if the Plugin should be enabled
	 * @return List of Plugins that are successfully loaded
	 * @see #loadPlugins(File)
	 */
	List<Future<Plugin>> loadPlugins(File directory, boolean enable);
	
	List<Future<Plugin>> loadPlugins(Collection<File> files, boolean enable);
	
	/**
	 * Unloads all Plugins and removes them from this PluginManager
	 * @param plugins to unload
	 * @return true if all plugins successfully unloaded
	 */
	boolean unloadPlugins(Plugin... plugins);
	
	/**
	 * Unloads the given Plugin and removes it from this PluginManager
	 * @param plugin to unload
	 * @return true if success
	 */
	boolean unloadPlugin(Plugin plugin);
	
	Collection<Plugin> getPlugins();
	
	Plugin getPlugin(String name);
	
	Collection<PluginLoader> getLoaders();
	
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
	
	void registerEvents(PluginHandle plugin, Listener listener);
	
	void registerEvents(PluginHandle plugin, Listener listener, Object... context);
	
	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener);
	
	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener,  Object... context);

	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority);

	<E extends Event> void registerFunctionalListener(PluginHandle plugin, Class<E> eventClass, FunctionalListener<E> listener, boolean ignoreCancelled, EventPriority priority, Object... context);	

	void removeEvents(PluginHandle handle);
	
	void callEvent(Event event);
	
	Set<NamespacedKey> getFeatures();
	
	boolean hasFeature(NamespacedKey feature);
	
	void addFeature(NamespacedKey feature);

	boolean removeFeature(NamespacedKey feature);
	
}
