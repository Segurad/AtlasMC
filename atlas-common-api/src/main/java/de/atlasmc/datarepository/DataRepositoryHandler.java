package de.atlasmc.datarepository;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Simplifies usage of multiple repositories
 */
public interface DataRepositoryHandler {
	
	/**
	 * Returns the local {@link CacheRepository}
	 * @return cache
	 */
	@NotNull
	CacheRepository getCache();
	
	@NotNull
	Collection<LocalRepository> getLocalRepos();
	
	@NotNull
	Collection<Repository> getRemoteRepos();
	
	void addRepo(Repository repo);
	
	void removeRepo(Repository repo);
	
	/**
	 * Returns a future of the requested entry
	 * @param key
	 * @return future
	 */
	@NotNull
	Future<RepositoryEntry> getEntry(NamespacedKey key);
	
	/**
	 * Returns a future of the requested entry
	 * @param key
	 * @param load whether or not the entry should be requested from remote
	 * @return future
	 */
	@NotNull
	Future<RepositoryEntry> getEntry(NamespacedKey key, boolean load);

	@NotNull
	Future<Collection<RepositoryEntry>> getEntries(Collection<NamespacedKey> keys);

	@Nullable
	LocalRepository getLocalRepo(String name);

	@Nullable
	Repository getRepo(String name);
	
	@Nullable
	Repository getRepo(UUID uuid);

	void addRepos(Collection<Repository> repositories);

}
