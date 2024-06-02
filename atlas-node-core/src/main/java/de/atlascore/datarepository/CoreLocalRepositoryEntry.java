package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.EntryFile;
import de.atlasmc.datarepository.RepositoryEntry;

public class CoreLocalRepositoryEntry implements RepositoryEntry {

	private final NamespacedKey key;
	private String description;
	private final Collection<EntryFile> files;
	private byte[] checksum;
	
	public CoreLocalRepositoryEntry(NamespacedKey key, String description, byte[] checksum, Collection<EntryFile> files) {
		this.key = key;
		this.checksum = checksum;
		this.files = files;
		this.description = description;
	}
	
	
	@Override
	public byte[] checksum() {
		return checksum;
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Collection<EntryFile> getFiles() {
		return files;
	}

	@Override
	public boolean isLocalAvailable() {
		return true;
	}

	@Override
	public boolean copyTo(File destination) throws IOException {
		return copyTo(destination, true);
	}
	
	@Override
	public boolean copyTo(File destination, boolean override) throws IOException {
		// copy files
		return false;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

}
