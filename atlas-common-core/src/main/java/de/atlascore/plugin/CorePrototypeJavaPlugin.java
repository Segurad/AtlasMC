package de.atlascore.plugin;

import java.io.File;
import java.io.IOException;

import de.atlasmc.plugin.AbstractPrototypePlugin;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.util.configuration.Configuration;

final class CorePrototypeJavaPlugin extends AbstractPrototypePlugin {

	private volatile Plugin plugin;
	private final CoreJavaPluginLoader loader;
	
	public CorePrototypeJavaPlugin(CoreJavaPluginLoader loader, File file, Configuration pluginInfo) {
		super(file, pluginInfo);
		if (loader == null)
			throw new IllegalArgumentException("Loader can not be null!");
		this.loader = loader;
	}

	@Override
	public Plugin create() throws IOException {
		Plugin plugin = this.plugin;
		if (plugin != null)
			return plugin;
		synchronized (this) {
			plugin = this.plugin;
			if (plugin != null)
				return plugin;
			plugin = loader.internalLoad(this);
			this.plugin = plugin;
			return plugin;
		}
	}

	@Override
	public boolean isCreated() {
		return plugin != null;
	}

	@Override
	public Plugin getPlugin() {
		return this.plugin;
	}
	
	@Override
	public CoreJavaPluginLoader getLoader() {
		return loader;
	}
	
	@Override
	public String toString() {
		return getName() + " " + getVersion();
	}

}
