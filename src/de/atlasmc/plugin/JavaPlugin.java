package de.atlasmc.plugin;

import java.io.File;

import de.atlasmc.event.Listener;

public class JavaPlugin implements Plugin {

	private File file;
	private PluginLoader loader;
	private ClassLoader classloader;
	private String name;
	private String version;
	private String author;
	private String description;
	private boolean loaded, enabled;
	
	@Override
	public final void load() {
		if (isLoaded())
			throw new IllegalStateException("Plugin already loaded!");
		loaded = true;
		onLoad();
	}

	@Override
	public final void enable() {
		if (isEnabled())
			throw new IllegalStateException("Plugin already enabled!");
		if (!isLoaded())
			throw new IllegalStateException("Plugin has to be loaded first!");
		enabled = true;
		onEnable();
	}

	@Override
	public final void disable() {
		if (!isEnabled())
			throw new IllegalStateException("Plugin already disabled!");
		enabled = false;
		onDisable();
	}

	@Override
	public final void unload() {
		if (!isLoaded())
			throw new IllegalStateException("Plugin already unloaded!");
		if (isEnabled())
			disable();
		loaded = false;
		onUnload();
	}
	
	/**
	 * Override this to get notified when this Plugin should be loaded.<br>
	 * During load all necessary systems should be created.
	 */
	protected void onLoad() {}

	/**
	 * Override this to get notified when this Plugin should be enabled.<br>
	 * During enable all systems like {@link Listener}s should be registered and set enabled.
	 */
	protected void onEnable() {}

	/**
	 * Override this to get notified when this Plugin should be disabled.<br>
	 * During disable all systems like {@link Listener}s should be unregistered and set disabled.
	 */
	protected void onDisable() {}

	/**
	 * Override this to get notified when this Plugin should be unloaded.<br>
	 * During unload all systems and resources should be freed for GC.
	 */
	protected void onUnload() {}

	@Override
	public final String getVersion() {
		return version;
	}

	@Override
	public final String getAuthor() {
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

	public final void init(File file, PluginLoader loader, ClassLoader classloader, String name, String version, String author, String description) {
		if (classloader != null)
			throw new PluginException("Plugin already initialized!");
		this.file = file;
		this.loader = loader;
		this.classloader = classloader;
		this.name = name;
		this.version = version;
		this.author = author;
		this.description = description;
	}

	@Override
	public final PluginLoader getPluginLoader() {
		return loader;
	}

	@Override
	public final ClassLoader getClassLoader() {
		return classloader;
	}

	@Override
	public File getFile() {
		return file;
	}

}
