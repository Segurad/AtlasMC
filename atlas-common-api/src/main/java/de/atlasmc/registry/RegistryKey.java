package de.atlasmc.registry;

import de.atlasmc.NamespacedAccessKey;
import de.atlasmc.NamespacedKey;

public class RegistryKey<T> extends NamespacedAccessKey<Registry<T>> {
	
	public RegistryKey(NamespacedKey key) {
		super(key);
	}

	@Override
	public Registry<T> get() {
		return Registries.getRegistry(key);
	}
	
	public <R extends Registry<T>> R getRegistry() {
		return Registries.getRegistry(key);
	}
	
	public T getValue(CharSequence key) {
		return get().get(key);
	}

}
