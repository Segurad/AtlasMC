package de.atlasmc.datarepository;

import java.util.Collection;

import de.atlasmc.util.annotation.NotNull;

public interface NamespaceStatus {
	
	@NotNull
	Collection<RepositoryEntry> touched();
	
	@NotNull
	Collection<String> untracked();
	
	@NotNull
	RepositoryNamespace namespace();
	
	void track(String key, String file);

}
