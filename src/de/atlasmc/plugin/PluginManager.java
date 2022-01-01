package de.atlasmc.plugin;

import java.io.File;
import java.util.List;

public interface PluginManager {
	
	/**
	 * Loads and returns the Plugin represented by the file or null if unable to load.<br>
	 * The returned Plugin is not active. It is required to call {@link Plugin#load()} and {@link Plugin#enable()}
	 * @param file
	 * @return Plugin or null
	 */
	public Plugin loadPlugin(File file);
	
	public void unloadPlugin(Plugin plugin);
	
	/**
	 * Loads all Plugins. Should only be called on startup.
	 * @param directory
	 * @return List of Plugins
	 * @see #loadPlugin(File)
	 */
	public List<Plugin> loadPlugins(File directory);
	
	/**
	 * Unloads all Plugins. Should only be called on stop.
	 */
	public void unloadPlugins(Plugin... plugins);
	
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
