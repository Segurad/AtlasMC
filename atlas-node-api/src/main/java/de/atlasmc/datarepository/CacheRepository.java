package de.atlasmc.datarepository;

import java.io.IOException;

import de.atlasmc.NamespacedKey;

public interface CacheRepository extends LocalRepository {

	boolean remove(RepositoryEntry entry) throws IOException;
	
	boolean remove(NamespacedKey entry) throws IOException;
	
	void clear() throws IOException;

	RepositoryEntry add(RepositoryEntry entry);

	void clearExpired() throws IOException;
	
}
