package de.atlasmc.plugin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface Plugin extends PluginHandle {
	
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
	
	/**
	 * Loads the configuration with the given context. 
	 * If the configuration is already loaded the context will be added to the configuration.
	 * A context may be anything.
	 * In case the configuration is loaded by a server the context will be the loading server.
	 * In case {@link #loadConfiguration(PluginConfiguration)} is used the context is the plugin.
	 * @param config to load
	 * @param context used to load the configuration
	 */
	void loadConfiguration(PluginConfiguration config, Object context);
	
	void unloadConfiguration(NamespacedKey config);
	
	/**
	 * Request unloading of a configuration. If any other context refers to the configuration it remains loaded
	 * @param config to unload
	 * @param context used to load the configuration
	 */
	void unloadConfiguration(NamespacedKey config, Object context);
	
	void reloadConfiguration(NamespacedKey config);
	
	void reloadConfigurations();
	
	@NotNull
	Collection<PluginConfiguration> getConfigurations();
	
	@NotNull
	Version getVersion();
	
	/**
	 * Returns a list of all Authors
	 * @return authors
	 */
	@NotNull
	List<String> getAuthor();
	
	/**
	 * Returns the plugins name
	 * @return name
	 */
	@NotNull
	String getName();
	
	/**
	 * Returns the plugins description or null of none
	 * @return description or null
	 */
	@Nullable
	String getDescription();
	
	boolean isLoaded();
	
	boolean isEnabled();
	
	@NotNull
	PluginLoader getPluginLoader();
	
	@NotNull
	PrototypePlugin getPrototype();

	@NotNull
	File getFile();
	
	@Nullable
	InputStream getResourceAsStream(String name);
	
	@Nullable
	URL getResource(String name);
	
	@NotNull
	Collection<PluginHandle> getHandles();

}
