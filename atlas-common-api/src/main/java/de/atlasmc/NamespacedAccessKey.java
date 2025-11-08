package de.atlasmc;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.annotation.Nullable;

/**
 * Base class for a accessing a value using a Namespaced key within a constant context
 * @param <T>
 */
public abstract class NamespacedAccessKey<T> implements Namespaced {
	
	/**
	 * The key used for retrieval of the value
	 */
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
	
	/**
	 * Returns the value of the key or null if not present
	 * @return value or null
	 */
	@Nullable
	public abstract T get();
	
	/**
	 * Whether or not the value exists
	 * @return true if exists
	 */
	public boolean exists() {
		return get() != null;
	}

}
