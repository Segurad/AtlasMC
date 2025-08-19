package de.atlasmc;

import de.atlasmc.NamespacedKey.Namespaced;

public abstract class NamespacedAccessKey<T> implements Namespaced {
	
	protected final NamespacedKey key;
	
	public NamespacedAccessKey(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public abstract T get();
	
	public boolean exists() {
		return get() != null;
	}

}
