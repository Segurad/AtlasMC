package de.atlasmc.plugin;

import java.io.File;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;

public class PluginConfiguration implements Namespaced {

	private final NamespacedKey key;
	private final File dir;
	
	public PluginConfiguration(NamespacedKey key, File dir) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (dir == null)
			throw new IllegalArgumentException("Dir can not be null!");
		this.key = key;
		this.dir = dir;
	}
	
	File getDirectory() {
		return dir;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PluginConfiguration other = (PluginConfiguration) obj;
		if (dir == null) {
			if (other.dir != null)
				return false;
		} else if (!dir.equals(other.dir))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

}
