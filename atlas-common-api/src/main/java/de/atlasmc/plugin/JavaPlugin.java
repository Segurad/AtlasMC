package de.atlasmc.plugin;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;

import de.atlasmc.Atlas;
import de.atlasmc.NamespacedKey;
import de.atlasmc.event.Listener;
import de.atlasmc.log.Log;
import de.atlasmc.util.annotation.InternalAPI;

public class JavaPlugin implements Plugin {

	private PrototypePlugin prototype;
	private Log logger;
	private List<String> author;
	private String description;
	private boolean loaded;
	private boolean enabled;
	
	protected JavaPlugin() {
		// override as needed
	}
	
	@Override
	public final void load() {
		if (isLoaded())
			return;
		onLoad();
		loaded = true;
	}

	@Override
	public final void enable() {
		if (isEnabled())
			return;
		if (!isEnabled())
			throw new IllegalStateException("Plugin has to be loaded first!");
		onEnable();
		enabled = true;
	}

	@Override
	public final void disable() {
		if (!isEnabled())
			return;
		onDisable();
		enabled = false;
	}

	@Override
	public final void unload() {
		if (!isLoaded())
			return;
		if (isEnabled())
			throw new IllegalStateException("Plugin has the be disabled first!");
		onUnload();
		unloadHandle(this);
		loaded = false;
	}
	
	/**
	 * Override to get notified when this Plugin should perform a internal reload.
	 */
	@Override
	public void reload() {
		// override as needed
	}
	
	/**
	 * Override this to get notified when this Plugin should be loaded.
	 * During load all necessary systems should be created.
	 */
	protected void onLoad() {
		// override as needed
	}

	/**
	 * Override this to get notified when this Plugin should be enabled.
	 * During enable all systems like {@link Listener}s should be registered and set enabled.
	 */
	protected void onEnable() {
		// override as needed
	}

	/**
	 * Override this to get notified when this Plugin should be disabled.
	 * During disable all systems like {@link Listener}s should be unregistered and set disabled.
	 */
	protected void onDisable() {
		// override as needed
	}

	/**
	 * Override this to get notified when this Plugin should be unloaded.
	 * During unload all systems and resources should be freed for GC.
	 */
	protected void onUnload() {
		// override as needed
	}

	@Override
	public final Version getVersion() {
		return prototype.getVersion();
	}

	@Override
	public final List<String> getAuthor() {
		return author;
	}

	@Override
	public final String getName() {
		return prototype.getName();
	}

	@Override
	public final String getDescription() {
		return description;
	}

	@Override
	public final boolean isLoaded() {
		return enabled;
	}

	@Override
	public final boolean isEnabled() {
		return loaded;
	}

	@InternalAPI
	public final synchronized void init(PrototypePlugin prototype, Log logger) {
		if (prototype == null)
			throw new IllegalArgumentException("Prototype can not be null!");
		if (logger == null)
			throw new IllegalArgumentException("Logger can not be null!");
		this.prototype = prototype;
		this.logger = logger;
	}

	@Override
	public final PluginLoader getPluginLoader() {
		return prototype.getLoader();
	}

	@Override
	public final File getFile() {
		return prototype.getFile();
	}
	
	@Override
	public final Log getLogger() {
		return logger;
	}
	
	@Override
	public PrototypePlugin getPrototype() {
		return prototype;
	}

	@Override
	public void loadConfiguration(PluginConfiguration config) {
		loadConfiguration(config, this);
	}

	@Override
	public void unloadConfiguration(NamespacedKey config) {
		unloadConfiguration(config, this);
	}

	@Override
	public void reloadConfiguration(NamespacedKey config) {
		// override as needed
	}
	
	@Override
	public void loadConfiguration(PluginConfiguration config, Object context) {
		// override as needed
	}

	@Override
	public void unloadConfiguration(NamespacedKey config, Object context) {
		// override as needed
	}

	@Override
	public void reloadConfigurations() {
		for (PluginConfiguration cfg : getConfigurations()) {
			reloadConfiguration(cfg.getNamespacedKey());
		}
	}
	
	@Override
	public Collection<PluginConfiguration> getConfigurations() {
		return List.of();
	}

	@Override
	public Plugin getPlugin() {
		return this;
	}
	
	protected void unloadHandle(PluginHandle handle) {
		Atlas.getPluginManager().removeEvents(handle);
		Atlas.getScheduler().removeTasks(handle);
	}

	@Override
	public Collection<PluginHandle> getHandles() {
		return List.of();
	}

	@Override
	public InputStream getResourceAsStream(String name) {
		return getClass().getResourceAsStream(name);
	}
	
	@Override
	public URL getResource(String name) {
		return getClass().getResource(name);
	}
	
	@Override
	public String toString() {
		return getName() + " " + getVersion();
	}

}
