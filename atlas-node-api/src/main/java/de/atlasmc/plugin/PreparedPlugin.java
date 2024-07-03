package de.atlasmc.plugin;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.util.concurrent.future.Future;
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
	
	void addDependency(Future<Plugin> dependency);
	
	boolean hasDependencies();
	
	void addSoftDependency(Future<Plugin> dependency);
	
	Collection<Future<Plugin>> getSoftDependencies();
	
	Collection<Future<Plugin>> getDependencies();
	
	boolean hasSoftDependencies();
	
	boolean isInvalid();
	
	void setInvalid();

	Plugin getPlugin();

}
