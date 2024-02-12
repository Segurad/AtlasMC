package de.atlascore.datarepository.local;

import java.io.File;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.PluginConfigEntry;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CorePluginConfigEntry extends CoreRepositoryEntry implements PluginConfigEntry {

	private final String pluginName;
	
	public CorePluginConfigEntry(File dir, NamespacedKey key, ConfigurationSection config) {
		super(dir, key, config);
		pluginName = config.getString("plugin-name");
		if (pluginName == null)
			throw new RepositoryException("Plugin entry without \"plugin-name\" key!");
	}

	@Override
	public String getPluginName() {
		return pluginName;
	}
	
	@Override
	public Configuration toConfiguration() {
		Configuration config = super.toConfiguration();
		config.set("plugin-name", pluginName);
		return config;
	}
	
	@Override
	protected String getType() {
		return "plugin-config";
	}

}
