package de.atlasmc.datarepository;

import de.atlasmc.NamespacedKey;

public interface LocalRepository extends Repository {
	
	PluginEntry getPlugin(NamespacedKey key);
	
	WorldEntry getWorld(NamespacedKey key);
	
	SchematicEntry getSchematic(NamespacedKey key); 
	
	PluginConfigEntry getPluginConfiguration(NamespacedKey key);
	
	RepositoryEntry getEntry(NamespacedKey key);

}
