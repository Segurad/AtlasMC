package de.atlasmc.plugin;

import java.io.IOException;
import java.util.Set;

import de.atlasmc.util.configuration.Configuration;

public interface PreparedPlugin {
	
	/**
	 * Loads the Plugin
	 * @return the Plugin
	 * @throws IOException 
	 */
	Plugin load() throws IOException;
	
	/**
	 * Return whether or not the Plugin is already loaded
	 * @return true if loaded
	 */
	boolean isLoaded();
	
	/**
	 * Returns the name if this Plugin
	 * @return name
	 */
	String getName();
	
	Configuration getPluginInfo();
	
	Set<PreparedPlugin> getDependencies();
	
	boolean hasDependencies();
	
	Set<PreparedPlugin> getSoftDependencies();
	
	boolean hasSoftDependencies();
	
	boolean isInvalid();
	
	void setInvalid();

	Plugin getPlugin();

}
