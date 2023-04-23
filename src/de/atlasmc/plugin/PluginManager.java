package de.atlasmc.plugin;

import java.io.File;
import java.util.List;

public interface PluginManager {
	
	/**
	 * Loads and returns the Plugin represented by the file or null if unable to load.<br>
	 * The returned Plugin is not active. It is required to call {@link Plugin#load()} and {@link Plugin#enable()}
	 * @param file file of the Plugin
	 * @param enable if the Plugin should be enabled
	 * @param checkDependencies if dependencies should be checked before loading
	 * @return Plugin or null if not successful
	 */
	public Plugin loadPlugin(File file, boolean enable, boolean checkDependencies);
	
	/**
	 * Unloads the given Plugin and removes it from this PluginManager
	 * @param plugin to unload
	 * @return true if success
	 */
	public boolean unloadPlugin(Plugin plugin);
	
	public default List<Plugin> loadPlugins(File directory) {
		return loadPlugins(directory, true, true);
	}
	
	/**
	 * Loads all Plugins. Should only be called on startup.
	 * @param directory of Plugin files
	 * @param enable if the Plugin should be enabled
	 * @param checkDependencies if dependencies should be checked before loading
	 * @return List of Plugins that are successfully loaded
	 * @see #loadPlugin(File)
	 */
	public List<Plugin> loadPlugins(File directory, boolean enable, boolean checkDependencies);
	
	/**
	 * Unloads all Plugins and removes them from this PluginManager
	 * @param plugins to unload
	 * @return true if all plugins successfully unloaded
	 */
	public boolean unloadPlugins(Plugin... plugins);
	
	public List<Plugin> getPlugins();
	
	public Plugin getPlugin(String name);
	
	public List<PluginLoader> getLoaders();
	
	/**
	 * Adds a PluginLoader
	 * @param loader
	 * @return success
	 */
	public boolean addLoader(PluginLoader loader);
	
	/**
	 * Removes a PluginLoader
	 * @param loader
	 * @return success
	 */
	public boolean removeLoader(PluginLoader loader);

}
