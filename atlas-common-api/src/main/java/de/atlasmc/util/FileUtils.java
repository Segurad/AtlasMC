package de.atlasmc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

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

	/**
	 * Checks if the given file exists and if it is a directory.
	 * If the file does not exist this will create the directory with {@link File#mkdirs()}
	 * @param file
	 * @thows {@link IllegalArgumentException} if the given file is present and is not a directory
	 */
	public static void ensureDir(File file) {
		if (file.exists()) {
			if (!file.isDirectory())
				throw new IllegalArgumentException("The given file does not represent a directory: " + file);
		} else {
			file.mkdirs();
		}
	}
	
	/**
	 * Checks if the given path exists and if it is a directory.
	 * If the path does not exist this will create the directory with {@link Files#createDirectories(Path, java.nio.file.attribute.FileAttribute...)}
	 * @param path
	 * @thows {@link IllegalArgumentException} if the given path is present and is not a directory
	 */
	public static void ensureDir(Path path) {
		if (Files.exists(path)) {
			if (!Files.isDirectory(path))
				throw new IllegalArgumentException("The given file does not represent a directory: " + path);
		} else {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}
	
	public static Set<Path> getFiles(Path path) throws IOException {
		Set<Path> result = new HashSet<>();
		try (Stream<Path> files = Files.list(path)) {
			files.forEach((file) -> {
				if (!Files.isRegularFile(file))
					return;
				result.add(file);
			});
		}
		if (result.isEmpty())
			return Set.of();
		return result;
	}

}
