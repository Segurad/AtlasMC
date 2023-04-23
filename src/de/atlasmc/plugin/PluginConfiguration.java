package de.atlasmc.plugin;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.configuration.Configuration;

public interface PluginConfiguration extends Namespaced {

	public Configuration getConfiguration();

}
