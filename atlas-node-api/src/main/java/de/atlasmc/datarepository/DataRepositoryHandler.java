package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.Future;

public interface DataRepositoryHandler {
	
	CacheRepository getCache();
	
	LocalRepository getLocal();
	
	Collection<Repository> getRemoteRepos();
	
	void addRepo(Repository repo);
	
	void removeRepo(Repository repo);
	
	Future<PluginEntry> getPlugin(NamespacedKey key, boolean load);
	
	Future<WorldEntry> getWorld(NamespacedKey key, boolean load);
	
	Future<SchematicEntry> getSchematic(NamespacedKey key, boolean load); 
	
	Future<PluginConfigEntry> getPluginConfiguration(NamespacedKey key, boolean load);
	
	Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load);

	Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys);

}
