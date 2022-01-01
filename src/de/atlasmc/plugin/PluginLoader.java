package de.atlasmc.plugin;

import java.io.File;
import java.io.IOException;

public interface PluginLoader {

	/**
	 * Returns whether or not this loader would be compatible with the file.
	 * @param file
	 * @return true if loadable
	 */
	public boolean canLoad(File file);
	
	/**
	 * Loads a Plugin and returns a representative instance e.g. the Main class.<br>
	 * If the loader was not able to load it because of a not supported format or an error it should return null. 
	 * @param file
	 * @return Plugin or null
	 * @throws IOException 
	 */
	public Plugin load(File file) throws IOException;

}
