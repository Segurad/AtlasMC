package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;

public interface SchematicEntry extends RepositoryEntry {

	public String getAuthor();
	
	public Collection<NamespacedKey> getDependencies();
	
}
