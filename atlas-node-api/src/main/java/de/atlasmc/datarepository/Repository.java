package de.atlasmc.datarepository;

import java.util.Collection;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.Future;

public interface Repository {
	
	Collection<String> getNamespaces();
	
	boolean isReadOnly();
	
	Future<? extends RepositoryEntry> getEntry(NamespacedKey key);

}
