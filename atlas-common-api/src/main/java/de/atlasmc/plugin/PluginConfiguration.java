package de.atlasmc.plugin;

import java.io.File;

import de.atlasmc.NamespacedKey.Namespaced;

public interface PluginConfiguration extends Namespaced {

	public File getDirectory();

}
