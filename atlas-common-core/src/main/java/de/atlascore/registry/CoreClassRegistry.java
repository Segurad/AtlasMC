package de.atlascore.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHolder.Target;

public class CoreClassRegistry<T> extends CoreAbstractRegistry<Class<T>> implements Registry<Class<T>> {

	public CoreClassRegistry(NamespacedKey key, Class<T> type) {
		super(key, type);
	}

	@Override
	public Target getTarget() {
		return Target.CLASS;
	}
	
	@Override
	protected void validateEntry(Class<T> value) {
		if (!type.isAssignableFrom(value))
			throw new IllegalArgumentException("Value (" + value.getClass().getName() + ") is not assignable from the registries type: " + type.getName());
	}

}
