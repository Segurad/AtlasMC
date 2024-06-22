package de.atlascore.datarepository;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.util.Pair;

public class CoreRepositoryEntryUpdate implements RepositoryEntryUpdate {
	
	private final NamespacedKey key;
	private final Collection<Pair<String, Change>> changes;
	private final Change change;
	
	public CoreRepositoryEntryUpdate(NamespacedKey key, Change change, Collection<Pair<String, Change>> changes) {
		this.key = key;
		this.change = change;
		this.changes = changes;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public Collection<Pair<String, Change>> getFilesChanged() {
		return changes;
	}

	@Override
	public boolean hasFilesChanged() {
		return changes != null && !changes.isEmpty();
	}

	@Override
	public Change getEntryChange() {
		return change;
	}

}
