package de.atlasmc.util.nbt.serialization.constructor;

import java.util.function.Function;

import de.atlasmc.registry.Registry;

public abstract class AbstractRegistryConstructor<T, K> implements Constructor<T> {

	protected final Registry<K> registry;
	protected final Function<K, T> constructor;

	public AbstractRegistryConstructor(Registry<K> registry, Function<K, T> constructor) {
		this.registry = registry;
		this.constructor = constructor;
	}

}
