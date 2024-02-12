package de.atlascore.datarepository;
import java.util.concurrent.ExecutionException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.PluginConfigEntry;
import de.atlasmc.datarepository.PluginEntry;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.datarepository.SchematicEntry;
import de.atlasmc.datarepository.WorldEntry;

public abstract class CoreAbstractLocalRepository implements LocalRepository {
	
	public CoreAbstractLocalRepository() {}

	@Override
	public PluginEntry getPlugin(NamespacedKey key) {
		RepositoryEntry entry = getEntry(key);
		if (entry == null)
			return null;
		return entry instanceof PluginEntry ? (PluginEntry) entry : null;
	}

	@Override
	public WorldEntry getWorld(NamespacedKey key) {
		RepositoryEntry entry = getEntry(key);
		if (entry == null)
			return null;
		return entry instanceof WorldEntry ? (WorldEntry) entry : null;
	}

	@Override
	public SchematicEntry getSchematic(NamespacedKey key) {
		RepositoryEntry entry = getEntry(key);
		if (entry == null)
			return null;
		return entry instanceof SchematicEntry ? (SchematicEntry) entry : null;
	}

	@Override
	public PluginConfigEntry getPluginConfiguration(NamespacedKey key) {
		RepositoryEntry entry = getEntry(key);
		if (entry == null)
			return null;
		return entry instanceof PluginConfigEntry ? (PluginConfigEntry) entry : null;
	}

	@Override
	public RepositoryEntry getEntry(NamespacedKey key) {
		try {
			return getRepoEntry(key).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new RepositoryException("Error while fetching entry!");
		}
	}

	@Override
	public boolean isReadOnly() {
		return false;
	}
	
}
