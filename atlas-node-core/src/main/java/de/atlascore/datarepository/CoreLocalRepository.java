package de.atlascore.datarepository;

import java.io.File;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.RepositoryEntry;

public class CoreLocalRepository extends CoreAbstractLocalRepository {
	
	public CoreLocalRepository(String name, File dir) {
		super(name, dir, false);
	}


	protected RepositoryEntry loadEntry(NamespacedKey key) {
		// TODO Auto-generated method stub
		return null;
	}

}
