package de.atlasmc.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;

/**
 * Represents a entry within a {@link Repository}.
 */
public interface RepositoryEntry extends Namespaced {
	
	/**
	 * Returns all files of this entry
	 * @return files
	 */
	Collection<EntryFile> getFiles();
	
	/**
	 * Returns whether or not this entry is local available. either a {@link LocalRepository} or a {@link CacheRepository}
	 * @return true if present
	 */
	boolean isLocalAvailable();
	
	/**
	 * 
	 * @see #copyTo(File, boolean)
	 * @param destination
	 * @return
	 * @throws IOException
	 */
	boolean copyTo(File destination) throws IOException;
	
	/**
	 * Copies all files of this entry to the given destination.
	 * The destination must be a directory.
	 * @param destination
	 * @param override whether or present files should be overwritten 
	 * @return true if successful
	 * @throws IOException if any io error occurs while copying the files
	 * @throws RepositoryException if {@link #isLocalAvailable()} is false. Make sure to re index or fetch if this error occurs
	 */
	boolean copyTo(File destination, boolean override) throws IOException;
	
	/**
	 * Returns a description of this entry or null
	 * @return description or null
	 */
	String getDescription();
	
	/**
	 * Returns a checksum of all {@link EntryFile#checksum()}
	 * @return checksum
	 */
	byte[] checksum();

}
