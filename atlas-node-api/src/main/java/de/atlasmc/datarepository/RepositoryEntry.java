package de.atlasmc.datarepository;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.Configuration;

public interface RepositoryEntry extends Namespaced {
	
	Collection<File> getFiles();
	
	boolean isLocalAvailable();
	
	/**
	 * Fetches the data from source returns a future.
	 * The reference may change during the fetch process. After fetching use the entry provided by the future.
	 * @return future
	 */
	Future<RepositoryEntry> fetch(CacheRepository cache);
	
	/**
	 * Copies all files of this entry to the given destination.
	 * The destination must be a directory.
	 * @param destination
	 * @throws IOException if any io error occurs while copying the files
	 * @throws RepositoryException if {@link #isLocalAvailable()} is false. Make sure to re index or fetch if this error occurs
	 */
	Future<Void> copyTo(File destination) throws IOException;
	
	String getDescription();

	Configuration toConfiguration();

}
