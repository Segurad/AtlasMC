package de.atlasmc.datarepository;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.Future;

public interface Repository {
	
	String getName();
	
	UUID getUUID();
	
	Collection<? extends RepositoryNamespace> getNamespaces();
	
	boolean isReadOnly();
	
	Future<RepositoryEntry> getEntry(NamespacedKey key);
	
	Future<Boolean> delete();

}
