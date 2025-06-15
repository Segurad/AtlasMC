package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;

public interface LocalRepository extends Repository {
	
	/**
	 * Creates a new namespace at the given path. 
	 * If a namespace with the given name already exists this method will return false.
	 * @param namespace
	 * @param path
	 * @return true if create
	 */
	boolean registerNamespace(String namespace, String path);

	@NotNull
	RepositoryNamespace getNamespace(String key);
	
	@NotNull
	RepositoryNamespace getNamespace(NamespacedKey key);

	@NotNull
	RepositoryEntry getLocalEntry(NamespacedKey key);

	/**
	 * Updates all entries of all namespaces
	 * @return returns a future of all updated entries
	 */
	@NotNull
	Collection<RepositoryEntryUpdate> update();

}
