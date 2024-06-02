package de.atlasmc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
	
	public static File extractConfiguration(File destDir, String filename, String resource, boolean override, Class<?> caller) throws IOException {
		File file = new File(destDir, filename);
		if (file.exists()) {
			if (override)
				file.delete();
			else
				return file;
		}
		InputStream resourceStream = caller.getResourceAsStream(resource);
		if (resourceStream == null)
			throw new FileNotFoundException("Resource not found: " + resource);
		Files.copy(resourceStream, file.toPath());
		return file;
	}
	
	/**
	 * Returns the child of the given path or null if not a valid child path.
	 * The child path may or may not exist
	 * @param parent
	 * @param child
	 * @return child path or null
	 */
	public static Path getSecurePath(Path parent, String child) {
		Path path = parent.resolve(child);
		if (parent.equals(path))
			return null;
		return path.startsWith(parent) ? path : null;
	}

}
