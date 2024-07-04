package de.atlascore.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ClassRegistry;
import de.atlasmc.registry.RegistryHolder.Target;

public class CoreClassRegistry<T> extends CoreAbstractRegistry<Class<T>> implements ClassRegistry<T> {

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

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getType() {
		return (Class<T>) super.getType();
	}

}
