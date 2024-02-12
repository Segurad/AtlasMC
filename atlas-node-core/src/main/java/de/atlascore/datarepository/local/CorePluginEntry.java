package de.atlascore.datarepository.local;

import java.io.File;
import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.PluginEntry;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CorePluginEntry extends CoreRepositoryEntry implements PluginEntry {

	private final String name;
	
	public CorePluginEntry(File dir, NamespacedKey key, ConfigurationSection config) {
		super(dir, key, config);
		name = config.getString("name");
		if (name == null)
			throw new RepositoryException("Plugin entry without \"name\" key!");
	}

	@Override
	public String getPluginName() {
		return name;
	}
	
	@Override
	public Configuration toConfiguration() {
		Configuration config = super.toConfiguration();
		config.set("name", name);
		return config;
	}
	
	@Override
	protected String getType() {
		return "plugin";
	}

}
