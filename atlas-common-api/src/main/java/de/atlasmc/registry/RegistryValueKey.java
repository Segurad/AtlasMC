package de.atlasmc.registry;

import de.atlasmc.NamespacedAccessKey;
import de.atlasmc.NamespacedKey;

public class RegistryValueKey<T> extends NamespacedAccessKey<T> {

	private final RegistryKey<T> registry;
	
	public RegistryValueKey(RegistryKey<T> registry, NamespacedKey key) {
		super(key);
		this.registry = registry;
	}

	@Override
	public T get() {
		return registry.getValue(key);
	}

}
