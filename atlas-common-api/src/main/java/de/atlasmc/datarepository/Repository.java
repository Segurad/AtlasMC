package de.atlasmc.datarepository;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.concurrent.future.Future;

public interface Repository {
	
	@NotNull
	String getName();
	
	@NotNull
	UUID getUUID();
	
	@NotNull
	RepositoryNamespace getNamespace(String key);
	
	@NotNull
	RepositoryNamespace getNamespace(NamespacedKey key);
	
	@NotNull
	Collection<RepositoryNamespace> getNamespaces();
	
	boolean isReadOnly();
	
	@NotNull
	Future<RepositoryEntry> getEntry(NamespacedKey key);

	@NotNull
	Future<Boolean> delete();

}
