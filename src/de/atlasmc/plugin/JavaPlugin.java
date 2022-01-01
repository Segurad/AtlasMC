package de.atlasmc.plugin;

import java.io.File;

public class JavaPlugin implements Plugin {

	private File file;
	private PluginLoader loader;
	private ClassLoader classloader;
	private String name;
	private String version;
	private String author;
	private String description;
	private boolean loaded, enabled;

	public JavaPlugin() {
		if (classloader != null)
			throw new PluginException("Plugin already initialized!");
	}
	
	@Override
	public final void load() {
		loaded = true;
	}

	@Override
	public final void enable() {
		enabled = true;
	}

	@Override
	public final void disable() {
		enabled = false;
	}

	@Override
	public final void unload() {
		loaded = false;
	}
	
	public void onLoad() {}

	public void onEnable() {}

	public void onDisable() {}

	public void onUnload() {}

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

	public final void init(File file ,PluginLoader loader, ClassLoader classloader, String name, String version, String author, String description) {
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
