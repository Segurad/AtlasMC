package de.atlasmc.datarepository;

/**
 * Represents a file of a {@link RepositoryEntry}
 */
public interface EntryFile {
	
	/**
	 * Returns the checksum of this file
	 * @return checksum
	 */
	byte[] checksum();
	
	/**
	 * Returns the time in milliseconds the file was last touched
	 * @return
	 */
	long lastTouch();
	
	/**
	 * Returns the relative path of the entry within the repository namespace
	 * @return path
	 */
	String file();
	
	/**
	 * Returns the size of the file in bytes
	 * @return size
	 */
	long size();

}
