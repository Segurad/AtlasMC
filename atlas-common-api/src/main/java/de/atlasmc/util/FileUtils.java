package de.atlasmc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import de.atlasmc.plugin.Plugin;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSection;

public class FileUtils {
	
	public static File extractResource(File dest, String resource, boolean override) throws IOException {
		String callerName = Thread.currentThread().getStackTrace()[2].getClassName();
		Class<?> caller;
		try {
			caller = Class.forName(callerName);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(e);
		}
		return extractResource(dest, resource, override, caller);
	}
	
	public static File extractResource(File dest, String resource, boolean override, Class<?> caller) throws IOException {
		if (dest.exists()) {
			if (override)
				dest.delete();
			else
				return dest;
		}
		InputStream resourceStream = caller.getResourceAsStream(resource);
		if (resourceStream == null)
			throw new FileNotFoundException("Resource not found: " + resource);
		Files.copy(resourceStream, dest.toPath());
		return dest;
	}
	
	public static void runSetupConfiguration(File dest, ConfigurationSection config, Plugin plugin) throws IOException {
		List<String> directories = config.getStringList("directories");
		Path destPath = dest.toPath();
		if (directories != null) {
			for (String dir : directories) {
				Path dirPath = getSecurePath(destPath, dir);
				if (dirPath != null)
					ensureDir(dirPath);
			}
		}
		List<String> extracts = config.getStringList("extract");
		for (String rawExtract : extracts) {
			int delimeter = rawExtract.indexOf(':');
			String resourceSrc = rawExtract.substring(0, delimeter);
			String resourceDest = rawExtract.substring(delimeter+1);
			Path resourceDestPath = getSecurePath(destPath, resourceDest);
			if (resourceDestPath == null)
				continue;
			InputStream in = plugin.getResourceAsStream(resourceSrc);
			if (in == null)
				continue;
			Files.copy(in, resourceDestPath);
		}
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
	
	/**
	 * Returns all regular files in the given directory. 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@NotNull
	public static Set<Path> getFiles(Path path) throws IOException {
		if (!Files.isDirectory(path))
			return Set.of();
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
