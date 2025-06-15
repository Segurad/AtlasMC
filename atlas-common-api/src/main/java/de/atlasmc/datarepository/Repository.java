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
	Collection<? extends RepositoryNamespace> getNamespaces();
	
	boolean isReadOnly();
	
	@NotNull
	Future<RepositoryEntry> getEntry(NamespacedKey key);

	@NotNull
	Future<Boolean> delete();

}
