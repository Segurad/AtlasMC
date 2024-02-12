package de.atlascore.datarepository;

import java.io.File;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.datarepository.RepositoryEntry;
import de.atlasmc.util.concurrent.future.Future;

public class CoreLocalRepository extends CoreAbstractLocalRepository {

	private final String repositoryKey;
	private final Path dirPath; 
	private final Map<String, Path> namespaces;
	
	public CoreLocalRepository(File dir, String repositoryKey) {
		super();
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		if (!dir.exists())
			dir.mkdirs();
		else if (!dir.isDirectory())
			throw new IllegalArgumentException("Given file is not a directory!");
		this.dirPath = dir.toPath();
		this.repositoryKey = repositoryKey;
		this.namespaces = new ConcurrentHashMap<>();
		init();
	}
	
	protected void init() {
		registerNamespace("worlds");
		registerNamespace("plugins");
		registerNamespace("plugin-configs");
		registerNamespace("server-templates");
	}
	
	public void registerNamespace(String namespace) {
		if (namespace == null)
			throw new IllegalArgumentException("Namespace can not be null!");
		String repoNamespace = repositoryKey + "/" + namespace;
		if (namespaces.containsKey(repoNamespace))
			return;
		Path path = getSecurePath(dirPath, namespace);
		if (path == null)
			throw new IllegalArgumentException("Namespace can not access files outside of this repository: " + namespace);
		File file = path.toFile();
		if (!file.exists()) {
			file.mkdirs();
		} else if (!file.isDirectory()) {
			throw new IllegalArgumentException("Namespace must be a directory: " + file.getAbsolutePath());
		}
		namespaces.put(repoNamespace, path);
	}
	
	private Path getSecurePath(Path parent, String child) {
		Path path = parent.resolve(child);
		if (parent.equals(path))
			return null;
		return path.startsWith(parent) ? path : null;
	}

	@Override
	public Collection<String> getNamespaces() {
		return namespaces.keySet();
	}

	@Override
	public Future<? extends RepositoryEntry> getRepoEntry(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!namespaces.containsKey(key.getNamespace()))
			throw new IllegalArgumentException("Repository does not support the given namespace: " + key.toString());
		return null;
	}

}
