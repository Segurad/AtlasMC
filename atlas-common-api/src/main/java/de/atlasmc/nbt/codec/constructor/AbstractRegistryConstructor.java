package de.atlasmc.nbt.codec.constructor;

import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.registry.RegistryKey;

public abstract class AbstractRegistryConstructor<T, K> implements Constructor<T> {

	protected final RegistryKey<K> registry;
	protected final Function<K, T> constructor;

	public AbstractRegistryConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		this.registry = Objects.requireNonNull(registry);
		this.constructor = Objects.requireNonNull(constructor);
	}

}
