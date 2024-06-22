package de.atlascore.datarepository;

import java.util.Collection;

import de.atlasmc.datarepository.NamespaceStatus;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryNamespace;

public class CoreNamespaceStatus implements NamespaceStatus {

	private final Collection<RepositoryEntry> touched;
	private final Collection<String> untracked;
	private final RepositoryNamespace namespace;
	
	public CoreNamespaceStatus(RepositoryNamespace namespace, Collection<RepositoryEntry> touched, Collection<String> untracked) {
		this.namespace = namespace;
		this.untracked = untracked;
		this.touched = touched;
	}
	
	@Override
	public Collection<RepositoryEntry> touched() {
		return touched;
	}

	@Override
	public Collection<String> untracked() {
		return untracked;
	}

	@Override
	public RepositoryNamespace namespace() {
		return namespace;
	}

	@Override
	public void track(String key, String file) {
		namespace.track(key, file);
	}

}
