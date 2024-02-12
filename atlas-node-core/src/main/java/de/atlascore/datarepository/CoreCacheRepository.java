package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.CacheRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.util.concurrent.future.Future;

public class CoreCacheRepository extends CoreAbstractLocalRepository implements CacheRepository {
	
	public CoreCacheRepository(File dir) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<String> getNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Future<? extends RepositoryEntry> getRepoEntry(NamespacedKey key) {
		// TODO Auto-generated method stub
		return null;
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

}
