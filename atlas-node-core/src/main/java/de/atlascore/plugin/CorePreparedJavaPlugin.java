package de.atlascore.plugin;

import java.io.File;
import java.io.IOException;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.util.configuration.Configuration;

public class CorePreparedJavaPlugin extends CoreAbstractPreparedPlugin {

	private final CoreJavaPluginLoader loader;
	private final File file;
	
	public CorePreparedJavaPlugin(Configuration pluginInfo, File file, CoreJavaPluginLoader loader) {
		super(pluginInfo);
		if (loader == null)
			throw new IllegalArgumentException("Loader can not be null!");
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		this.file = file;
		this.loader = loader;
	}

	@Override
	public Plugin load() throws IOException {
		if (isLoaded() || tryedToLoad)
			return plugin;
		tryedToLoad = true;
		plugin = loader.loadPreparedPlugin(this);
		return plugin;
	}

	public File getFile() {
		return file;
	}

}
