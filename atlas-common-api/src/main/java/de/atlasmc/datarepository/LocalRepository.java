package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;

public interface LocalRepository extends Repository {
	
	/**
	 * Creates a new namespace at the given path. 
	 * If a namespace with the given name already exists this method will return false.
	 * @param namespace
	 * @param path
	 * @return true if create
	 */
	boolean registerNamespace(String namespace, String path);

	RepositoryNamespace getNamespace(String key);
	
	RepositoryNamespace getNamespace(NamespacedKey key);

	RepositoryEntry getLocalEntry(NamespacedKey key);

	/**
	 * Updates all entries of all namespaces
	 * @return returns a future of all updated entries
	 */
	Collection<RepositoryEntryUpdate> update();

}
