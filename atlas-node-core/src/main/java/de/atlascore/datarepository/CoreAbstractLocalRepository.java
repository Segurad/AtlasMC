package de.atlascore.datarepository;
import java.io.File;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.cache.CacheHolder;
import de.atlasmc.cache.Caching;
import de.atlasmc.datarepository.EntryFile;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.datarepository.RepositoryEntryUpdate;
import de.atlasmc.datarepository.RepositoryNamespace;
import de.atlasmc.util.DeleteFileVisitor;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.reference.WeakReference1;

public abstract class CoreAbstractLocalRepository implements LocalRepository, CacheHolder {
	
	private final String name;
	private final UUID uuid;
	protected final File metaDir;
	private final File namespaceFile;
	protected final Path dirPath; 
	protected final Map<String, CoreLocalNamespace> namespaces;
	protected final Map<NamespacedKey, WeakReference1<CoreLocalRepositoryEntry, NamespacedKey>> entryCache;
	protected final boolean readonly;
	private final ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
	
	private YamlConfiguration namespacesConfig;
	
	public CoreAbstractLocalRepository(String name, UUID uuid, File dir, boolean readonly) {
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		FileUtils.ensureDir(dir);
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		this.name = name;
		this.uuid = uuid;
		this.dirPath = dir.toPath();
		this.readonly = readonly;
		this.namespaces = new ConcurrentHashMap<>();
		this.entryCache = new ConcurrentHashMap<>();
		// init meta dir
		this.metaDir = new File(dir, ".datarepo");
		this.namespaceFile = new  File(metaDir, "namespaces.yml");
		Caching.register(this);
		init();
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	protected void init() {
		// create meta dir
		FileUtils.ensureDir(metaDir);
		// load existing namespaces
		if (namespaceFile.exists()) {
			try {
				namespacesConfig = YamlConfiguration.loadConfiguration(namespaceFile);
			} catch (IOException e) {
				throw new IllegalStateException("Error while loading namespaces.yml", e);
			}
			for (String key : namespacesConfig.getKeys()) {
				registerNamespace(key, namespacesConfig.getString(key), false);
			}
		} else {
			namespacesConfig = new YamlConfiguration();
		}
	}
	
	@Override
	public boolean registerNamespace(String namespace, String path) {
		return registerNamespace(namespace, path, true);
	}
	
	/**
	 * Registers a namespace for this repository.
	 * @param namespace
	 * @param path
	 * @param write true if register new namespace. false if loading existing namespace
	 */
	private boolean registerNamespace(String namespace, String path, boolean write) {
		if (namespace == null)
			throw new IllegalArgumentException("Namespace can not be null!");
		if (!NamespacedKey.NAMESPACE_PATTERN.matcher(namespace).matches())
			throw new IllegalArgumentException("Invalid namespace: " + namespace);
		if (namespaces.containsKey(namespace))
			return false;
		Path namespacePath = FileUtils.getSecurePath(dirPath, path);
		if (namespacePath == null)
			throw new IllegalArgumentException("Path can not access files outside of this repository: " + namespace);
		FileUtils.ensureDir(namespacePath);
		File namespaceMetaDir = new File(metaDir, path);
		FileUtils.ensureDir(namespaceMetaDir);
		namespaces.put(namespace, new CoreLocalNamespace(this, namespace, namespacePath, namespaceMetaDir));
		// persist new namespace in meta dir
		if (write) {
			namespacesConfig.set(namespace, path);
			try {
				namespacesConfig.save(namespaceFile);
			} catch (IOException e) {
				throw new IllegalStateException("Error while writing namespaces.yml", e);
			}
		}
		return true;
	}

	@Override
	public Collection<CoreLocalNamespace> getNamespaces() {
		return namespaces.values();
	}
	
	@Override
	public RepositoryNamespace getNamespace(String key) {
		return namespaces.get(key);
	}
	
	@Override
	public RepositoryNamespace getNamespace(NamespacedKey key) {
		return namespaces.get(key.getKey());
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		CoreLocalRepositoryEntry entry = getCachedEntry(key);
		if (entry != null)
			return CompleteFuture.of(entry);
		try {
			entry = loadEntry(key);
		} catch (IOException e) {
			return new CompleteFuture<>(e);
		}
		entryCache.put(key, new WeakReference1<>(entry, refQueue, key));
		return CompleteFuture.of(entry);
	}
	
	protected CoreLocalRepositoryEntry loadEntry(NamespacedKey key) throws IOException {
		CoreLocalNamespace namespace = namespaces.get(key.getNamespace());
		if (namespace == null)
			throw new IllegalArgumentException("Repository does not support the given namespace: " + key.toString());
		ConfigurationSection rawEntry = namespace.loadEntry(key);
		return createEntry(key, rawEntry);
	}
	
	protected CoreLocalRepositoryEntry getCachedEntry(NamespacedKey key) {
		WeakReference<CoreLocalRepositoryEntry> ref = entryCache.get(key);
		if (ref != null) {
			CoreLocalRepositoryEntry entry = ref.get();
			if (entry != null)
				return entry;
			entryCache.remove(key);
		}
		return null;
	}

	@Override
	public boolean isReadOnly() {
		return readonly;
	}
	
	protected CoreLocalRepositoryEntry createEntry(NamespacedKey key, ConfigurationSection config) {
		return new CoreLocalRepositoryEntry(this, key, config);
	}
	
	protected boolean copyEntry(CoreLocalRepositoryEntry entry, File destination, boolean override) throws IOException {
		FileUtils.ensureDir(destination);
		CoreLocalNamespace namespace = namespaces.get(entry.getNamespace());
		Path nsPath = namespace.getPath();
		Path destPath = destination.toPath();
		for (EntryFile file : entry.getFiles()) {
			String filePath = file.file();
			Path srcPath = nsPath.resolve(filePath);
			Path path = destPath.resolve(filePath);
			if (override) {
				Files.copy(srcPath, path, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
			} else {
				Files.copy(srcPath, path, StandardCopyOption.COPY_ATTRIBUTES);
			}
		}
		return true;
	}

	protected Future<Boolean> delete(CoreLocalRepositoryEntry entry) {
		CoreLocalNamespace ns = namespaces.get(entry.getNamespace());
		if (ns == null)
			return CompleteFuture.of(false);
		try {
			if (ns.delete(entry.getKey())) {
				WeakReference<CoreLocalRepositoryEntry> ref = entryCache.remove(entry.getNamespacedKey());
				if (ref != null)
					ref.clear();
				return CompleteFuture.of(true);
			}
		} catch (IOException e) {
			return new CompleteFuture<>(e);
		}
		return CompleteFuture.of(false);
	}

	protected boolean isTouched(CoreLocalRepositoryEntry entry, boolean shallow) throws IOException {
		CoreLocalNamespace ns = namespaces.get(entry.getNamespace());
		if (ns == null)
			return false;
		return ns.isTouched(entry, shallow);
	}

	protected Future<RepositoryEntryUpdate> update(CoreLocalRepositoryEntry entry) {
		CoreLocalNamespace ns = namespaces.get(entry.getNamespace());
		if (ns == null)
			return CompleteFuture.getNullFuture();
		return ns.update(entry, readonly);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void cleanUp() {
		WeakReference1<?, NamespacedKey> ref = null;
		while ((ref = (WeakReference1<?, NamespacedKey>) refQueue.poll()) != null) {
			entryCache.remove(ref.value1, ref);
		}
	}
	
	@Override
	public Future<Collection<RepositoryEntryUpdate>> update() {
		ArrayList<RepositoryEntryUpdate> changes = new ArrayList<>();
		for (CoreLocalNamespace ns : namespaces.values()) {
			try {
				changes.addAll(ns.internalUpdate());
			} catch(IOException e) {
				// TODO handle exceptions
			}
		}
		return CompleteFuture.of(changes);
	}
	
	@Override
	public Future<Boolean> delete() {
		try {
			Files.walkFileTree(metaDir.toPath(), DeleteFileVisitor.INSTANCE);
			for (CoreLocalNamespace namespace : namespaces.values())
				namespace.internalDelete();
		} catch(IOException e) {
			return new CompleteFuture<>(e);
		}
		return CompleteFuture.of(true);
	}

	protected void removeNamespace(CoreLocalNamespace namespace) throws IOException {
		namespaces.remove(namespace.getNamespace());
		namespacesConfig.remove(namespace.getNamespace());
		namespacesConfig.save(namespaceFile);
	}
	
}
