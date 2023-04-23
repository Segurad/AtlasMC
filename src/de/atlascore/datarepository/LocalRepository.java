package de.atlascore.datarepository;

import java.io.File;
import java.util.Map;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.RepositoryEntry;

public class LocalRepository {
	
	private final File dir;
	private Map<NamespacedKey, File> entries;
	private Map<NamespacedKey, RepositoryEntry> cache;
	
	
	public LocalRepository(File dir) {
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		if (!dir.exists())
			dir.mkdirs();
		else if (!dir.isDirectory())
			throw new IllegalArgumentException("Given file is not a directory!");
		this.dir = dir;
		indexRepo();
	}
	
	public void indexRepo() {
		
	}

}
