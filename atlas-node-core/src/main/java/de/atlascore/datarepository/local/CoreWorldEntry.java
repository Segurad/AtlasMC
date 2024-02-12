package de.atlascore.datarepository.local;

import java.io.File;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.datarepository.WorldEntry;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreWorldEntry extends CoreRepositoryEntry implements WorldEntry {

	private final String name;
	private final String format;
	
	public CoreWorldEntry(File dir, NamespacedKey key, ConfigurationSection config) {
		super(dir, key, config);
		name = config.getString("name");
		format = config.getString("format");
		if (name == null)
			throw new RepositoryException("Plugin entry without \"name\" key!");
		if (format == null)
			throw new RepositoryException("Plugin entry without \"format\" key!");
	}

	@Override
	public String getWorldName() {
		return name;
	}

	@Override
	public String getWorldFormat() {
		return format;
	}
	
	@Override
	public Configuration toConfiguration() {
		Configuration config = super.toConfiguration();
		config.set("name", name);
		config.set("format", format);
		return config;
	}
	
	@Override
	protected String getType() {
		return "world";
	}

}
