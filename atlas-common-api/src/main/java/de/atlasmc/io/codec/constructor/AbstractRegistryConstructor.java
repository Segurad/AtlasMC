package de.atlasmc.io.codec.constructor;

import java.util.function.Function;

import de.atlasmc.registry.RegistryKey;

public abstract class AbstractRegistryConstructor<T, K> implements Constructor<T> {

	protected final RegistryKey<K> registry;
	protected final Function<K, T> constructor;

	public AbstractRegistryConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		this.registry = registry;
		this.constructor = constructor;
	}

}
