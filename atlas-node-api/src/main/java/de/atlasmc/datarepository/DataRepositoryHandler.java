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
	
	Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load);

	Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys);

}
