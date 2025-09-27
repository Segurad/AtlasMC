package de.atlasmc.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.configuration.ConfigurationException;
import de.atlasmc.util.configuration.ConfigurationSection;

public class FileUtils {
	
	public static final boolean CONFIG_OVERRIDE;
	
	public static final FileFilter 
	JSON_FILE_FILTER = new ExtensionFileFilter("json"),
	YAML_FILE_FILTER = new ExtensionFileFilter("yml", "yaml");
	
	static {
		CONFIG_OVERRIDE = Boolean.getBoolean("atlas.config.override");
	}
	
	public static File extractResource(File dest, String resource) throws IOException {
		return extractResource(dest, resource, CONFIG_OVERRIDE);
	}
	
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
		runSetupConfiguration(dest, config, plugin, CONFIG_OVERRIDE);
	}
	
	public static void runSetupConfiguration(File dest, ConfigurationSection config, Plugin plugin, boolean override) throws IOException {
		final List<?> directories = config.getList("directories");
		final Path destPath = dest.toPath();
		if (directories != null && !directories.isEmpty()) {
			final Log log = plugin.getLogger();
			for (Object entry : directories) {
				String dir;
				List<String> files = null;
				if (entry instanceof ConfigurationSection section) {
					dir = section.getString("dir");
					files = section.getStringList("files");
				} else if (entry instanceof String str) {
					dir = str;
				} else {
					throw new ConfigurationException("Invalid directory value: " + entry);
				}
				final Path dirPath = getSecurePath(destPath, dir);
				if (dirPath == null)
					continue;
				boolean dirCreated = ensureDir(dirPath);
				if (dirCreated)
					log.debug("Setup created dir: {}", dirPath);
				if ((dirCreated || override) && files != null && !files.isEmpty()) {
					extractFiles(dirPath, files, plugin, override);
				}
			}
		}
		final List<String> extracts = config.getStringList("files");
		if (extracts != null && !extracts.isEmpty())
			extractFiles(destPath, extracts, plugin, override);
	}
	
	private static void extractFiles(Path destPath, List<String> files, Plugin plugin, boolean override) throws IOException {
		final Log log = plugin.getLogger();
		for (String rawExtract : files) {
			int delimeter = rawExtract.indexOf(':');
			String resourceSrc = rawExtract.substring(0, delimeter);
			String resourceDest = rawExtract.substring(delimeter+1);
			Path resourceDestPath = getSecurePath(destPath, resourceDest);
			if (resourceDestPath == null)
				continue;
			if (Files.exists(resourceDestPath)) {
				if (override) {
					Files.delete(resourceDestPath);
				} else {
					continue;
				}
			}
			InputStream in = plugin.getResourceAsStream(resourceSrc);
			if (in == null) {
				log.warn("Failed to extract resource: {} not found!", resourceSrc);
				continue;
			}
			Files.copy(in, resourceDestPath);
			log.debug("Setup extracted resource: {}", resourceDestPath);
		}
	}
	
	/**
	 * Returns the child of the given path or null if not a valid child path.
	 * The child path may or may not exist
	 * @param parent
	 * @param child
	 * @return child path or null
	 */
	@Nullable
	public static Path getSecurePath(@NotNull Path parent, @NotNull String child) {
		Path path = parent.resolve(child);
		if (parent.equals(path))
			return null;
		return path.startsWith(parent) ? path : null;
	}

	/**
	 * Checks if the given file exists and if it is a directory.
	 * If the file does not exist this will create the directory with {@link File#mkdirs()}
	 * @param file
	 * @return true if the directories where created otherwise false
	 * @thows {@link IllegalArgumentException} if the given file is present and is not a directory
	 */
	public static boolean ensureDir(@NotNull File file) {
		if (file.exists()) {
			if (!file.isDirectory())
				throw new IllegalArgumentException("The given file does not represent a directory: " + file);
			return false;
		} else {
			return file.mkdirs();
		}
	}
	
	/**
	 * Checks if the given path exists and if it is a directory.
	 * If the path does not exist this will create the directory with {@link Files#createDirectories(Path, java.nio.file.attribute.FileAttribute...)}
	 * @param path
	 * @return true if the directories where created otherwise false
	 * @thows {@link IllegalArgumentException} if the given path is present and is not a directory
	 */
	public static boolean ensureDir(@NotNull Path path) {
		if (Files.exists(path)) {
			if (!Files.isDirectory(path))
				throw new IllegalArgumentException("The given file does not represent a directory: " + path);
			return false;
		} else {
			try {
				Files.createDirectories(path);
				return true;
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
	public static Set<Path> getFiles(@NotNull Path path) throws IOException {
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
	
	public static void deleteDir(@NotNull Path path) throws IOException {
		Files.walkFileTree(path, DeleteFileVisitor.INSTANCE);
	}

	public static void deleteDir(@NotNull File file) throws IOException {
		if (file == null)
			throw new IllegalArgumentException("File can not be null!");
		deleteDir(file.toPath());
	}
	
	@NotNull
	public static FileVisitor<Path> deleteVisitor() {
		return DeleteFileVisitor.INSTANCE;
	}

}
