package de.atlasmc.datarepository;

import java.util.Collection;
import java.util.UUID;

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
	
	/**
	 * Returns a future of the requested entry
	 * @param key
	 * @return future
	 */
	Future<RepositoryEntry> getEntry(NamespacedKey key);
	
	/**
	 * Returns a future of the requested entry
	 * @param key
	 * @param load whether or not the entry should be requested from remote
	 * @return future
	 */
	Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load);

	Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys);

	LocalRepository getLocalRepo(String name);

	Repository getRepo(String name);
	
	Repository getRepo(UUID uuid);

	void addRepos(Collection<Repository> repositories);

}
