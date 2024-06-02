package de.atlascore.datarepository;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.LocalRepository;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.util.FileUtils;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public abstract class CoreAbstractLocalRepository implements LocalRepository {
	
	private final String name;
	protected final File metaDir;
	private final File namespaceFile;
	protected final Path dirPath; 
	protected final Map<String, CoreLocalNamespace> namespaces;
	protected final Map<NamespacedKey, CoreLocalRepositoryEntry> entryCache;
	protected final boolean readonly;
	
	private YamlConfiguration namespacesConfig;
	
	public CoreAbstractLocalRepository(String name, File dir, boolean readonly) {
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		if (!dir.exists())
			dir.mkdirs();
		else if (!dir.isDirectory())
			throw new IllegalArgumentException("Given file is not a directory!");
		if (name == null)
			throw new IllegalArgumentException("Name can not be null!");
		this.name = name;
		this.dirPath = dir.toPath();
		this.readonly = readonly;
		this.namespaces = new ConcurrentHashMap<>();
		this.entryCache = new ConcurrentHashMap<>();
		// init meta dir
		this.metaDir = new File(dir, ".datarepo");
		this.namespaceFile = new  File(metaDir, "namespaces.yml");
		init();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	protected void init() {
		// create meta dir
		if (metaDir.exists()) {
			if (!metaDir.isDirectory())
				throw new IllegalStateException(".datarepo is not a directory at: " + metaDir.getAbsolutePath());
		} else {
			metaDir.mkdirs();
		}
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
	public void registerNamespace(String namespace, String path) {
		registerNamespace(namespace, path, true);
	}
	
	/**
	 * Registers a namespace for this repository.
	 * @param namespace
	 * @param path
	 * @param write true if register new namespace. false if loading existing namespace
	 */
	private void registerNamespace(String namespace, String path, boolean write) {
		if (namespace == null)
			throw new IllegalArgumentException("Namespace can not be null!");
		if (!NamespacedKey.NAMESPACE_PATTERN.matcher(namespace).matches())
			throw new IllegalArgumentException("Invalid namespace: " + namespace);
		if (namespaces.containsKey(namespace))
			return;
		Path namespacePath = FileUtils.getSecurePath(dirPath, path);
		if (namespacePath == null)
			throw new IllegalArgumentException("Path can not access files outside of this repository: " + namespace);
		File file = namespacePath.toFile();
		if (!file.exists()) {
			file.mkdirs();
		} else if (!file.isDirectory()) {
			throw new IllegalArgumentException("Path must be a directory: " + file.getAbsolutePath());
		}
		namespaces.put(namespace, new CoreLocalNamespace(namespace, namespacePath, new File(metaDir, path)));
		// persist new namespace in meta dir
		if (write) {
			namespacesConfig.set(namespace, path);
			try {
				namespacesConfig.save(namespaceFile);
			} catch (IOException e) {
				throw new IllegalStateException("Error while writing namespaces.yml", e);
			}
		}
	}

	@Override
	public Collection<String> getNamespaces() {
		return namespaces.keySet();
	}

	@Override
	public Future<RepositoryEntry> getEntry(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		CoreLocalNamespace namespace = namespaces.get(key.getNamespace());
		if (namespace == null)
			throw new IllegalArgumentException("Repository does not support the given namespace: " + key.toString());
		CoreLocalRepositoryEntry entry = entryCache.get(key);
		if (entry != null)
			return CompleteFuture.of(entry);
		ConfigurationSection rawEntry = null;
		try {
			rawEntry = namespace.loadEntry(key);
		} catch (IOException e) {
			return new CompleteFuture<>(e);
		}
		try {
			entry = createEntry(key, rawEntry);
		} catch(Exception e) {
			return new CompleteFuture<>(e);
		}
		if (entry == null)
			return CompleteFuture.getNullFuture();
		entryCache.put(key, entry);
		return CompleteFuture.of(entry);
	}

	@Override
	public boolean isReadOnly() {
		return readonly;
	}
	
	protected CoreLocalRepositoryEntry createEntry(NamespacedKey key, ConfigurationSection config) {
		// TODO create entry
		return null;
	}
	
}
