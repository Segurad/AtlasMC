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
	
	public static <T> RegistryValueKey<T> of(RegistryKey<T> registry, String key) {
		return new RegistryValueKey<>(registry, NamespacedKey.of(key));
	}
	
	public static <T> RegistryValueKey<T> ofLiteral(RegistryKey<T> registry, String key) {
		return new RegistryValueKey<>(registry, NamespacedKey.literal(key));
	}

}
