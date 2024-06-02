package de.atlascore.datarepository.local;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.CacheRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfiguration;

public class CoreRepositoryEntry implements RepositoryEntry {

	protected final File baseDir;
	protected final NamespacedKey key;
	protected final String description;
	protected final Collection<File> files;
	
	public CoreRepositoryEntry(File dir, RepositoryEntry entry) {
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		baseDir = dir;
		key = entry.getNamespacedKey();
		description = entry.getDescription();
		files = List.copyOf(entry.getFiles());
	}
	
	public CoreRepositoryEntry(File dir, NamespacedKey key, ConfigurationSection config) {
		if (dir == null)
			throw new IllegalAccessError("Dir can not be null!");
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		this.baseDir = dir;
		this.key = key;
		this.description = config.getString("description");
		List<String> files = config.getStringList("files");
		if (files == null)
			this.files = new ArrayList<>();
		else
			this.files = new ArrayList<>(files.size());
		for (String file : files)
			this.files.add(new File(file));
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public Collection<File> getFiles() {
		return files;
	}

	@Override
	public boolean isLocalAvailable() {
		return true;
	}

	@Override
	public Future<RepositoryEntry> fetch(CacheRepository cache) {
		return new CompleteFuture<>(this);
	}

	@Override
	public Future<Void> copyTo(File destination) {
		if (destination == null)
			throw new IllegalArgumentException("Destination can not be null!");
		if (destination.exists() && !destination.isDirectory())
			throw new IllegalArgumentException("Destination must be a directory!");
		if (isLocalAvailable())
			throw new RepositoryException("Entry is local not available.");
		Path destPath = destination.toPath();
		Throwable err = null;
		try {
			for (File file : files) {
				Files.copy(Path.of(baseDir.getPath(), file.getPath()), destPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);	
			}
		} catch (Exception e) {
			err = e;
		}
		if (err == null)
			return CompleteFuture.getNullFuture();
		return new CompleteFuture<>(err, false);
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public Configuration toConfiguration() {
		MemoryConfiguration config = new MemoryConfiguration();
		config.set("description", description);
		ArrayList<String> files = new ArrayList<>(this.files.size());
		for (File file : this.files) {
			files.add(file.getPath());
		}
		String type = getType();
		if (type != null)
			config.set("type", type);
		config.set("files", files);
		config.set("key", getKey());
		return config;
	}
	
	protected String getType() {
		return null;
	}

}
