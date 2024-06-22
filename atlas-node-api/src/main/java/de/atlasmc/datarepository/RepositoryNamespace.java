package de.atlasmc.datarepository;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.concurrent.future.Future;

public interface RepositoryNamespace {
	
	String getNamespace();
	
	Future<RepositoryEntry> getEntry(NamespacedKey key);
	
	Future<RepositoryEntry> getEntry(String key);
	
	Future<RepositoryEntry> track(String key, String file);
	
	NamespaceStatus getStatus() throws IOException;

	Future<Collection<RepositoryEntryUpdate>> update();

}
