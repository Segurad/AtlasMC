package de.atlasmc.datarepository;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey;

public interface RepositoryNamespace {
	
	String getNamespace();
	
	RepositoryEntry getEntry(NamespacedKey key);
	
	RepositoryEntry getEntry(String key);
	
	RepositoryEntry track(String key, String file);
	
	NamespaceStatus getStatus() throws IOException;

	Collection<RepositoryEntryUpdate> update() throws IOException;

	boolean delete() throws IOException;

}
