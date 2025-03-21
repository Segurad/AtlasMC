package de.atlasmc.plugin;

import java.io.File;
import java.io.IOException;

public interface PluginLoader {

	/**
	 * Returns whether or not this loader would be compatible with the file.
	 * @param file
	 * @return true if loadable
	 */
	boolean canLoad(File file);
	
	/**
	 * Loads a Plugin and returns a representative instance e.g. the Main class.<br>
	 * If the loader was not able to load it because of a not supported format or an error it should return null. 
	 * @param file
	 * @return Plugin or null if unable to load
	 * @throws IOException 
	 */
	Plugin load(File file) throws IOException;
	
	/**
	 * Prepares a Plugin to be loaded later with this loader
	 * @param file
	 * @return PreparedPlugin or null if unable to load
	 * @throws IOException
	 */
	PrototypePlugin preparePlugin(File file) throws IOException;

	/**
	 * Notifies the loader that the plugin is unloaded
	 * @param plugin
	 */
	void unload(Plugin plugin);

}
