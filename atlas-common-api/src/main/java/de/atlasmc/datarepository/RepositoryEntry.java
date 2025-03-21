package de.atlasmc.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Represents a entry within a {@link Repository}.
 */
public interface RepositoryEntry extends Namespaced {
	
	/**
	 * Returns the repository of this entry.
	 * @return repository
	 */
	@NotNull
	Repository getRepository();
	
	/**
	 * Returns all files of this entry
	 * @return files
	 */
	@NotNull
	List<? extends EntryFile> getFiles();
	
	/**
	 * Returns whether or not this entry is local available. either a {@link LocalRepository} or a {@link CacheRepository}
	 * @return true if present
	 */
	boolean isLocalAvailable();

	/**
	 * Returns whether or not this entry is a directory
	 * @return true if directory
	 */
	boolean isDirectory();
	
	/**
	 * Copies all files of this entry to the given destination.
	 * The destination must be a directory.
	 * Existing files will not be overwritten.
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
	@Nullable
	String getDescription();
	
	/**
	 * Returns a checksum of all {@link EntryFile#checksum()}
	 * @return checksum
	 */
	@NotNull
	byte[] checksum();
	
	boolean matchChecksum(byte[] checksum);
	
	@NotNull
	Future<Boolean> delete();
	
	/**
	 * @see #isTouched(boolean)
	 * @return true if touched
	 * @throws IOException 
	 */
	boolean isTouched() throws IOException;
	
	/**
	 * Returns whether or not this entry has been modified locally
	 * @param shallow whether or not all checksums should be calculated
	 * @return true if touched
	 * @throws IOException 
	 */
	boolean isTouched(boolean shallow) throws IOException;
	
	/**
	 * Updates all files of this entry
	 * @return future with this entry
	 */
	@NotNull
	Future<RepositoryEntryUpdate> update();

	/**
	 * Tries to make this entry available to this node. Returns a future containing a entry.
	 * The entry provided by the future should be used for further usage.
	 * @return future entry
	 */
	@NotNull
	Future<RepositoryEntry> makeAvailable();

}
