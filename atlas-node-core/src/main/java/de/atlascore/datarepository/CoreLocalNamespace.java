package de.atlascore.datarepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 *  A namespace of a local data repository
 */
class CoreLocalNamespace {
	
	private final String namespace;
	private final Path path;
	protected final File metaDir;
	
	public CoreLocalNamespace(String namespace, Path path, File metaDir) {
		this.namespace = namespace;
		this.path = path;
		this.metaDir = metaDir;
	}
	
	public String getNamespace() {
		return namespace;
	}
	
	public Path getPath() {
		return path;
	}

	public ConfigurationSection loadEntry(NamespacedKey key) throws IOException {
		return null;
	}

}
