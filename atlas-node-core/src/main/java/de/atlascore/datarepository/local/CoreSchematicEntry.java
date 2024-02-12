package de.atlascore.datarepository.local;

import java.io.File;
import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.SchematicEntry;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreSchematicEntry extends CoreRepositoryEntry implements SchematicEntry {

	public CoreSchematicEntry(File dir, NamespacedKey key, ConfigurationSection config) {
		super(dir, key, config);
	}
	
	@Override
	protected String getType() {
		return "schematic";
	}

}
