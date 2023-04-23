package de.atlasmc.datarepository;

import java.util.Collection;
import java.util.function.Consumer;

public interface Repository {
	
	public Collection<String> getKeys();
	
	public boolean isReadOnly();
	
	public void getEntry(Consumer<RepositoryEntry> callback);

}
