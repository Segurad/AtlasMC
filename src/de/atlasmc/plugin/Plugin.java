package de.atlasmc.plugin;

import java.io.File;

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
	
	public String getVersion();
	
	public String getAuthor();
	
	public String getName();
	
	public String getDescription();
	
	public boolean isLoaded();
	
	public boolean isEnabled();
	
	public PluginLoader getPluginLoader();
	
	public ClassLoader getClassLoader();
	
	public File getFile();

}
