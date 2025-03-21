package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.CacheRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.util.concurrent.future.Future;

public class CoreCacheRepository extends CoreAbstractLocalRepository implements CacheRepository {
	
	public CoreCacheRepository(File dir) {
		super("cache", UUID.randomUUID(), dir, false);
	}

	@Override
	public boolean remove(RepositoryEntry entry) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(NamespacedKey entry) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RepositoryEntry add(RepositoryEntry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearExpired() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<RepositoryEntryUpdate> update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<Boolean> delete() {
		// TODO Auto-generated method stub
		return null;
	}

}
