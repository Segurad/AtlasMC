package de.atlasmc.core.datarepository;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestException;
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
	
	private static final String HASH_ALGORITHM = "md5";
	private static final int DIGEST_BUFFER_SIZE = 0x1000;
	
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
		return JsonConfiguration.loadConfiguration(new File(metaDir, key.key()));
	}

	@Override
	public RepositoryEntry getEntry(NamespacedKey key) {
		if (!namespace.equals(key.namespace()))
			return null;
		return repo.getLocalEntry(key);
	}

	@Override
	public RepositoryEntry getEntry(String key) {
		return repo.getLocalEntry(NamespacedKey.of(namespace, key));
	}

	@Override
	public RepositoryEntry track(String key, String file) {
		File metaFile = new File(metaDir, key + ".json"); 
		if (metaFile.exists())
			throw new RepositoryException("Entry with name " + key + " already exist");
		Path entryPath = FileUtils.getSecurePath(path, file);
		if (entryPath == null)
			throw new RepositoryException("Path does not represent a path within the namespace");
		if (!Files.exists(entryPath))
			throw new RepositoryException("Path does not exist!");
		NamespacedKey nskey = NamespacedKey.of(namespace, key);
		JsonConfiguration config = new JsonConfiguration();
		config.set("key", nskey.toString());
		config.set("root", path.relativize(entryPath).toString().replace('\\', '/'));
		config.set("description", null);
		ArrayList<ConfigurationSection> entryFiles = new ArrayList<>();
		config.set("files", entryFiles);
		try {
			MessageDigest totalDigest = MessageDigest.getInstance(HASH_ALGORITHM);
			MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
			byte[] buff = new byte[DIGEST_BUFFER_SIZE];
			int digestLength = md.getDigestLength();
			byte[] checksumBuff = digestLength == 0 ? null : new byte[digestLength];
			Stream<Path> files = Files.walk(entryPath);
			files.forEach((entryFilePath) -> {
				if (Files.isDirectory(entryFilePath))
					return;
				try {
					BasicFileAttributes attributes = Files.readAttributes(entryFilePath, BasicFileAttributes.class);
					long lastTouch = attributes.lastModifiedTime().toMillis();
					long size = attributes.size();
					byte[] rawChecksum = fileChecksum(md, buff, entryFilePath, checksumBuff);
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
			throw new RepositoryException("Error while calclating checksum!", e);
		}
		try {
			config.save(metaFile);
		} catch (IOException e) {
			throw new RepositoryException("Error while writing meta file", e);
		}
		RepositoryEntry entry = repo.createEntry(nskey, config);
		return entry;
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
			CoreRepositoryEntry entry = repo.createEntry(key, cfg);
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
		FileUtils.deleteDir(fileRoot);
		return true;
	}

	protected boolean isTouched(CoreRepositoryEntry entry, boolean shallow) throws IOException {
		MessageDigest md = null; 
		byte[] buff = null; 
		Set<Path> files = null;
		byte[] checksumBuff = null;
		if (!shallow) {
			try {
				md = MessageDigest.getInstance(HASH_ALGORITHM);
			} catch (NoSuchAlgorithmException e) {
				throw new RepositoryException(e);
			}
			buff = new byte[DIGEST_BUFFER_SIZE];
			files = FileUtils.getFilesRecursive(path.resolve(entry.getRoot()));
			int digestLength = md.getDigestLength();
			checksumBuff = digestLength == 0 ? null : new byte[digestLength];
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
				byte[] rawChecksum = fileChecksum(md, buff, filePath, checksumBuff);
				if (!file.matchChecksum(rawChecksum))
					return true;
			}
		}
		return !shallow && !files.isEmpty();
	}

	public Future<RepositoryEntryUpdate> update(CoreRepositoryEntry entry, boolean shallow) {
		try {
			return CompleteFuture.of(internalUpdate(entry, shallow));
		} catch(IOException e) {
			return new CompleteFuture<>(e);
		}
	}
	
	protected RepositoryEntryUpdate internalUpdate(CoreRepositoryEntry entry, boolean shallow) throws IOException {
		Path entryRoot = path.resolve(entry.getRoot());
		if (!Files.exists(entryRoot)) {
			File metaFile = new File(metaDir, entry.getNamespacedKey().key() + ".json");
			metaFile.delete();
			return new CoreRepositoryEntryUpdate(entry.getNamespacedKey(), Change.DELETED, List.of());
		}
		final MessageDigest totalChecksum;
		final MessageDigest md; 
		final byte[] buff = new byte[DIGEST_BUFFER_SIZE];
		final Set<Path> files = FileUtils.getFilesRecursive(path.resolve(entry.getRoot()));
		final byte[] checksumBuff;
		try {
			totalChecksum = MessageDigest.getInstance(HASH_ALGORITHM);
			md = MessageDigest.getInstance(HASH_ALGORITHM);
			int digestLength = md.getDigestLength();
			checksumBuff = digestLength == 0 ? null : new byte[digestLength];
		} catch (NoSuchAlgorithmException e) {
			throw new RepositoryException(e);
		}
		boolean updated = false;
		Collection<? extends CoreEntryFile> entries = entry.getFiles();
		Iterator<? extends CoreEntryFile> entryIter = entries.iterator();
		ArrayList<Pair<String, Change>> changes = new ArrayList<>();
		while (entryIter.hasNext()) {
			CoreEntryFile file = entryIter.next();
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
				byte[] rawChecksum = fileChecksum(md, buff, filePath, checksumBuff);
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
				byte[] checksum = fileChecksum(md, buff, filePath, checksumBuff);
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
			File metaFile = new File(metaDir, entry.getNamespacedKey().key() + ".json");
			entry.toConfiguration(new JsonConfiguration()).save(metaFile);
			change = Change.CHANGED;
		}
		return new CoreRepositoryEntryUpdate(entry.getNamespacedKey(), change, changes);
	}
	
	private static byte[] fileChecksum(MessageDigest md, byte[] buff, Path path, byte[] checksumBuff) throws IOException {
		try (InputStream in = Files.newInputStream(path)) {
			while (in.available() > 0) {
				int bytesRead = in.read(buff);
				md.update(buff, 0, bytesRead);
			}
			if (checksumBuff == null)
				return md.digest();
			md.digest(checksumBuff, 0, checksumBuff.length);
			return checksumBuff; 
		} catch (DigestException e) {
			throw new RepositoryException("Failed to create checksum!", e);
		}
	}

	@Override
	public Collection<RepositoryEntryUpdate> update() throws IOException {
		return internalUpdate();
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
			CoreRepositoryEntry entry = repo.getCachedEntry(key);
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

	@Override
	public Future<Boolean> delete() {
		try {
			internalDelete();
			repo.removeNamespace(this);
		} catch(IOException e) {
			return new CompleteFuture<>(e);
		}
		return CompleteFuture.of(true);
	}
	
	protected void internalDelete() throws IOException {
		FileUtils.deleteDir(metaDir);
		FileUtils.deleteDir(path);
	}

}
