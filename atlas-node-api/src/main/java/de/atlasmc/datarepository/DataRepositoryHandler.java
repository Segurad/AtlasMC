package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Simplifies usage of multiple repositories
 */
public interface DataRepositoryHandler {
	
	/**
	 * Returns the local {@link CacheRepository}
	 * @return cache
	 */
	CacheRepository getCache();
	
	Collection<LocalRepository> getLocalRepos();
	
	Collection<Repository> getRemoteRepos();
	
	void addRepo(Repository repo);
	
	void removeRepo(Repository repo);
	
	Future<RepositoryEntry> getEntry(NamespacedKey key);
	
	Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load);

	Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys);

}
