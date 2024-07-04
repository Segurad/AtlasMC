package de.atlasmc.plugin;

import java.io.File;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;

public interface Plugin {
	
	/**
	 * Called directly after the Plugin is loaded.
	 */
	void load();
	
	/**
	 * Called when the Plugin should be enabled.
	 */
	void enable();
	
	/**
	 * Called when the Plugin should be disabled.
	 */
	void disable();
	
	/**
	 * Called when the Plugin gets unloaded.
	 */
	void unload();
	
	/**
	 * Called when the Plugin should reload all internal configurations
	 */
	void reload();
	
	void loadConfiguration(PluginConfiguration config);
	
	void unloadConfiguration(NamespacedKey config);
	
	void reloadConfiguration(NamespacedKey config);
	
	void reloadConfigurations();
	
	String getVersion();
	
	List<String> getAuthor();
	
	String getName();
	
	String getDescription();
	
	boolean isLoaded();
	
	boolean isEnabled();
	
	PluginLoader getPluginLoader();
	
	File getFile();
	
	Log getLogger();

}
