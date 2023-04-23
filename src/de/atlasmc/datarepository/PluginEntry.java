package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;

public interface PluginEntry extends RepositoryEntry {
	
	public String getAuthor();
	
	public String getVersion();
	
	public String getPluginName();
	
	public Collection<NamespacedKey> getDependencies();

}
