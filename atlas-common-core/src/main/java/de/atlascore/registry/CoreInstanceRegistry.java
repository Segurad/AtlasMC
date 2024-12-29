package de.atlascore.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder.Target;

public class CoreInstanceRegistry<T> extends CoreAbstractRegistry<T> implements Registry<T> {

	public CoreInstanceRegistry(NamespacedKey key, Class<?> type) {
		super(key, type);
	}

	@Override
	public Target getTarget() {
		return Target.INSTANCE;
	}

	@Override
	protected void validateEntry(T value) {
		if (!type.isInstance(value))
			throw new IllegalArgumentException("Value (" + value.getClass().getName() + ") is not a instance of the registries type: " + type.getName());
	}

}
