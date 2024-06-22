package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.EntryFile;
import de.atlasmc.datarepository.NamespaceStatus;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.datarepository.RepositoryEntryUpdate.Change;
import de.atlasmc.datarepository.RepositoryException;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.util.DeleteFileVisitor;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.Pair;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.MemoryConfigurationSection;
import de.atlasmc.util.configuration.file.JsonConfiguration;

/**
 *  A namespace of a local data repository
 */
class CoreLocalNamespace implements RepositoryNamespace {
	
	private final CoreAbstractLocalRepository repo;
	private final String namespace;
	private final Path path;
	protected final File metaDir;
	
	public CoreLocalNamespace(CoreAbstractLocalRepository repo, String namespace, Path path, File metaDir) {
		this.repo = repo;
		this.namespace = namespace;
		this.path = path;
		this.metaDir = metaDir;
	}
	
	@Override
	public String getNamespace() {
		return namespace;
	}
	
	public Path getPath() {
		return path;
	}

	protected ConfigurationSection loadEntry(NamespacedKey key) throws IOException {
		return JsonConfiguration.loadConfiguration(new File(metaDir, key.getKey()));
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key) {
		if (!namespace.equals(key.getNamespace()))
			return CompleteFuture.getNullFuture();
		return repo.getEntry(key);
	}

	@Override
	public Future<RepositoryEntry> getEntry(String key) {
		return repo.getEntry(NamespacedKey.of(namespace, key));
	}

	@Override
	public Future<RepositoryEntry> track(String key, String file) {
		File metaFile = new File(metaDir, key + ".json"); 
		if (metaFile.exists())
			return new CompleteFuture<>(new RepositoryException("Entry with name " + key + " already exist"));
		Path entryPath = FileUtils.getSecurePath(path, file);
		if (entryPath == null)
			return new CompleteFuture<>(new RepositoryException("Path does not represent a path within the namespace"));
		if (!Files.exists(entryPath))
			return new CompleteFuture<>(new RepositoryException("Path does not exist!"));
		NamespacedKey nskey = NamespacedKey.of(namespace, key);
		JsonConfiguration config = new JsonConfiguration();
		config.set("key", nskey.toString());
		config.set("root", path.relativize(entryPath).toString().replace('\\', '/'));
		config.set("description", null);
		ArrayList<ConfigurationSection> entryFiles = new ArrayList<>();
		config.set("files", entryFiles);
		try {
			MessageDigest totalDigest = MessageDigest.getInstance("md5");
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] buff = new byte[0x1000];
			Stream<Path> files = Files.walk(entryPath);
			files.forEach((entryFilePath) -> {
				if (Files.isDirectory(entryFilePath))
					return;
				try {
					BasicFileAttributes attributes = Files.readAttributes(entryFilePath, BasicFileAttributes.class);
					long lastTouch = attributes.lastModifiedTime().toMillis();
					long size = attributes.size();
					byte[] rawChecksum = fileChecksum(md5, buff, entryFilePath);
					totalDigest.update(rawChecksum);
					String checksum = HexFormat.of().formatHex(rawChecksum);
					MemoryConfigurationSection fileCfg = new MemoryConfigurationSection(config);
					fileCfg.set("lastTouch", lastTouch);
					fileCfg.set("size", size);
					fileCfg.set("checksum", checksum);
					fileCfg.set("file", path.relativize(entryFilePath).toString().replace('\\', '/'));
					entryFiles.add(fileCfg);
				} catch (IOException e) {
					throw new RepositoryException(e);
				}
			});
			files.close();
			byte[] rawTotalChecksum = totalDigest.digest();
			config.set("checksum", HexFormat.of().formatHex(rawTotalChecksum));
		} catch (IOException | NoSuchAlgorithmException e) {
			return new CompleteFuture<>(e);
		}
		try {
			config.save(metaFile);
		} catch (IOException e) {
			return new CompleteFuture<>(new RepositoryException("Error while writing meta file", e));
		}
		RepositoryEntry entry = repo.createEntry(nskey, config);
		return CompleteFuture.of(entry);
	}
	
	@Override
	public NamespaceStatus getStatus() throws IOException {
		ArrayList<RepositoryEntry> touchedEntries = new ArrayList<>();
		Set<Path> roots = new HashSet<>();
		File[] files = metaDir.listFiles();
		for (File meta : files) {
			if (!meta.isFile())
				continue;
			if (!meta.getName().endsWith(".json"))
				continue;
			JsonConfiguration cfg = JsonConfiguration.loadConfiguration(meta);
			NamespacedKey key = NamespacedKey.of(cfg.getString("key"));
			CoreLocalRepositoryEntry entry = repo.createEntry(key, cfg);
			roots.add(path.resolve(entry.getRoot()));
			if (isTouched(entry, true)) {
				touchedEntries.add(entry);
			}
		}
		ArrayList<String> untracked = new ArrayList<>();
		Stream<Path> paths = Files.list(path);
		paths.forEach((path) -> {
			if (roots.contains(path))
				return;
			untracked.add(this.path.relativize(path).toString());
		});
		paths.close();
		return new CoreNamespaceStatus(this, touchedEntries, untracked);
	}

	protected boolean delete(String key) throws IOException {
		File metaFile = new File(metaDir, key + ".json");
		if (!metaFile.exists())
			return false;
		JsonConfiguration config = JsonConfiguration.loadConfiguration(metaFile);
		Path fileRoot = path.resolve(config.getString("root"));
		Files.walkFileTree(fileRoot, DeleteFileVisitor.INSTANCE);
		return true;
	}

	protected boolean isTouched(CoreLocalRepositoryEntry entry, boolean shallow) throws IOException {
		final MessageDigest md5; 
		final byte[] buff; 
		final Set<Path> files;
		if (!shallow) {
			try {
				md5 = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) {
				throw new RepositoryException(e);
			}
			buff = new byte[0x1000];
			files = FileUtils.getFiles(path.resolve(entry.getRoot()));
		} else {
			md5 = null;
			buff = null;
			files = null;
		}
		for (EntryFile file : entry.getFiles()) {
			Path filePath = path.resolve(file.file());
			if (!Files.exists(filePath))
				return true;
			BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
			if (attributes.lastModifiedTime().toMillis() != file.lastTouch())
				return true;
			if (attributes.size() != file.size())
				return true;
			if (!shallow) {
				files.remove(filePath);
				byte[] rawChecksum = fileChecksum(md5, buff, filePath);
				if (!file.matchChecksum(rawChecksum))
					return true;
			}
		}
		if (!shallow && !files.isEmpty())
			return true;
		return false;
	}

	public Future<RepositoryEntryUpdate> update(CoreLocalRepositoryEntry entry, boolean shallow) {
		try {
			return CompleteFuture.of(internalUpdate(entry, shallow));
		} catch(IOException e) {
			return new CompleteFuture<>(e);
		}
	}
	
	protected RepositoryEntryUpdate internalUpdate(CoreLocalRepositoryEntry entry, boolean shallow) throws IOException {
		Path entryRoot = path.resolve(entry.getRoot());
		if (!Files.exists(entryRoot)) {
			File metaFile = new File(metaDir, entry.getKey() + ".json");
			metaFile.delete();
			return new CoreRepositoryEntryUpdate(entry.getNamespacedKey(), Change.DELETED, List.of());
		}
		final MessageDigest totalChecksum;
		final MessageDigest md5; 
		final byte[] buff = new byte[0x1000];
		final Set<Path> files = FileUtils.getFiles(path.resolve(entry.getRoot()));
		try {
			totalChecksum = MessageDigest.getInstance("md5");
			md5 = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			throw new RepositoryException(e);
		}
		boolean updated = false;
		Collection<? extends CoreLocalEntryFile> entries = entry.getFiles();
		Iterator<? extends CoreLocalEntryFile> entryIter = entries.iterator();
		ArrayList<Pair<String, Change>> changes = new ArrayList<>();
		while (entryIter.hasNext()) {
			CoreLocalEntryFile file = entryIter.next();
			Path filePath = path.resolve(file.file());
			if (!Files.exists(filePath)) {
				entryIter.remove();
				updated = true;
				changes.add(Pair.of(file.file(), Change.DELETED));
				continue;
			}
			// if not shallow always re-calc checksum
			boolean fileUpdated = false;
			BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
			if (attributes.lastModifiedTime().toMillis() != file.lastTouch()) {
				fileUpdated = true;
			} else if (attributes.size() != file.size()) {
				fileUpdated = true;
			}
			if (fileUpdated || !shallow) {
				file.setLastTouch(attributes.lastModifiedTime().toMillis());
				file.setSize(attributes.size());
				byte[] rawChecksum = fileChecksum(md5, buff, filePath);
				totalChecksum.update(rawChecksum);
				if (!file.matchChecksum(rawChecksum))
					fileUpdated = true;
			} else {
				totalChecksum.update(file.checksum());
			}
			if (fileUpdated)
				changes.add(Pair.of(file.file(), Change.CHANGED));
			files.remove(filePath);
		}
		if (!files.isEmpty()) {
			updated = true;
			for (Path filePath : files) {
				BasicFileAttributes attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
				String rawFile = path.relativize(filePath).toString().replace('\\', '/');
				long lastTouch = attributes.lastModifiedTime().toMillis();
				long size = attributes.size();
				byte[] checksum = fileChecksum(md5, buff, filePath);
				totalChecksum.update(checksum);
				entry.addFile(rawFile, lastTouch, checksum, size);
				changes.add(Pair.of(rawFile, Change.CREATED));
			}
		}
		byte[] totalChecksumData = totalChecksum.digest();
		if (!entry.matchChecksum(totalChecksumData)) {
			updated = true;
			entry.setChecksum(totalChecksumData);
		}
		Change change = null;
		if (updated) {
			File metaFile = new File(metaDir, entry.getKey() + ".json");
			entry.toConfiguration(new JsonConfiguration()).save(metaFile);
			change = Change.CHANGED;
		}
		return new CoreRepositoryEntryUpdate(entry.getNamespacedKey(), change, changes);
	}
	
	private static byte[] fileChecksum(MessageDigest md5, byte[] buff, Path path) throws IOException {
		try (InputStream in = Files.newInputStream(path)) {
			while (in.available() > 0) {
				int bytesRead = in.read(buff);
				md5.update(buff, 0, bytesRead);
			}
			return md5.digest();
		}	
	}

	@Override
	public Future<Collection<RepositoryEntryUpdate>> update() {
		try {
			return CompleteFuture.of(internalUpdate());
		} catch(IOException e) {
			return new CompleteFuture<>(e);
		}	
	}

	protected Collection<RepositoryEntryUpdate> internalUpdate() throws IOException {
		ArrayList<RepositoryEntryUpdate> changes = null;
		File[] files = metaDir.listFiles();
		for (File meta : files) {
			if (!meta.isFile())
				continue;
			String fileName = meta.getName();
			if (!fileName.endsWith(".json"))
				continue;
			NamespacedKey key = NamespacedKey.of(namespace, fileName.replace(".json", ""));
			CoreLocalRepositoryEntry entry = repo.getCachedEntry(key);
			if (entry == null) {
				JsonConfiguration cfg = JsonConfiguration.loadConfiguration(meta);
				entry = repo.createEntry(key, cfg);
			}
			RepositoryEntryUpdate update = internalUpdate(entry, false);
			if (update.getEntryChange() == null && !update.hasFilesChanged())
				continue;
			if (changes == null)
				changes = new ArrayList<>();
			changes.add(update);
		}
		return changes;
	}

}
