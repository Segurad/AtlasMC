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

	private Log logger;
	private File file;
	private PluginLoader loader;
	private String name;
	private String version;
	private List<String> author;
	private String description;
	private boolean loaded;
	private boolean enabled;
	private volatile boolean keepLoaded;
	
	protected JavaPlugin() {}
	
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
	public void reload() {}
	
	/**
	 * Override this to get notified when this Plugin should be loaded.
	 * During load all necessary systems should be created.
	 */
	protected void onLoad() {}

	/**
	 * Override this to get notified when this Plugin should be enabled.
	 * During enable all systems like {@link Listener}s should be registered and set enabled.
	 */
	protected void onEnable() {}

	/**
	 * Override this to get notified when this Plugin should be disabled.
	 * During disable all systems like {@link Listener}s should be unregistered and set disabled.
	 */
	protected void onDisable() {}

	/**
	 * Override this to get notified when this Plugin should be unloaded.
	 * During unload all systems and resources should be freed for GC.
	 */
	protected void onUnload() {}

	@Override
	public final String getVersion() {
		return version;
	}

	@Override
	public final List<String> getAuthor() {
		return author;
	}

	@Override
	public final String getName() {
		return name;
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
	public final synchronized void init(File file, PluginLoader loader, Log logger, String name, String version, List<String> author, String description) {
		if (this.loader != null)
			throw new IllegalStateException("Plugin already initialized!");
		if (loader == null)
			throw new IllegalArgumentException("Loader can not be null!");
		this.file = file;
		this.loader = loader;
		this.name = name;
		this.version = version;
		this.author = author;
		this.description = description;
		this.logger = logger;
	}

	@Override
	public final PluginLoader getPluginLoader() {
		return loader;
	}

	@Override
	public final File getFile() {
		return file;
	}
	
	@Override
	public final Log getLogger() {
		return logger;
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
	public void reloadConfiguration(NamespacedKey config) {}
	
	@Override
	public void loadConfiguration(PluginConfiguration config, Object context) {}

	@Override
	public void unloadConfiguration(NamespacedKey config, Object context) {}

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
	public boolean isKeepLoaded() {
		return keepLoaded;
	}

	@Override
	public void setKeepLoaded(boolean keeploaded) {
		this.keepLoaded = keeploaded;
	}

}
