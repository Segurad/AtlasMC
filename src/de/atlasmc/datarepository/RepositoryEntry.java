package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;

public interface RepositoryEntry extends Namespaced {
	
	public Collection<String> getFiles();
	
	public String getMD5(String file);
	
	public boolean isLocalAvailable();
	
	public String getDescription();

}
