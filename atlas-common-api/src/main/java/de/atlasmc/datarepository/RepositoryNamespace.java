package de.atlasmc.datarepository;

import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;

public interface RepositoryNamespace {
	
	@NotNull
	String getNamespace();
	
	@NotNull
	RepositoryEntry getEntry(NamespacedKey key);
	
	@NotNull
	RepositoryEntry getEntry(String key);
	
	@NotNull
	RepositoryEntry track(String key, String file);
	
	@NotNull
	NamespaceStatus getStatus() throws IOException;

	@NotNull
	Collection<RepositoryEntryUpdate> update() throws IOException;

	boolean delete() throws IOException;

}
