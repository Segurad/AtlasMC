package de.atlasmc.plugin;

import java.io.File;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;

public interface Plugin {
	
	/**
	 * Called directly after the Plugin is loaded.
	 */
	public void load();
	
	/**
	 * Called when the Plugin should be enabled.
	 */
	public void enable();
	
	/**
	 * Called when the Plugin should be disabled.
	 */
	public void disable();
	
	/**
	 * Called when the Plugin gets unloaded.
	 */
	public void unload();
	
	/**
	 * Called when the Plugin should reload all internal configurations
	 */
	public void reload();
	
	public void loadConfiguration(PluginConfiguration config);
	
	public void unloadConfiguration(NamespacedKey config);
	
	public void reloadConfiguration(NamespacedKey config);
	
	public void reloadConfigurations();
	
	public String getVersion();
	
	public List<String> getAuthor();
	
	public String getName();
	
	public String getDescription();
	
	public boolean isLoaded();
	
	public boolean isEnabled();
	
	public PluginLoader getPluginLoader();
	
	public File getFile();
	
	public Log getLogger();

}
