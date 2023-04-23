package de.atlasmc.datarepository;

import de.atlasmc.NamespacedKey;

public interface LocalRepository {
	
	public PluginEntry getPlugin(NamespacedKey key);
	
	public WorldEntry getWorld(NamespacedKey key);
	
	public SchematicEntry getSchematic(NamespacedKey key); 
	
	public PluginConfigurationEntry getPluginConfiguration(NamespacedKey key);
	
	public RepositoryEntry getEntry(NamespacedKey key);
	
	public void reindex();

}
