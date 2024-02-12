package de.atlascore.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHandler;
import de.atlasmc.registry.RegistryHolder;

public class CoreRegistryHandler implements RegistryHandler {
	
	@SuppressWarnings("rawtypes")
	private final Registry<Registry> registries;
	
	public CoreRegistryHandler() {
		registries = new CoreRegistry<>(NamespacedKey.of(NamespacedKey.ATLAS, "registry_root"), Registry.class);
	}

	@Override
	public <T> Registry<T> getRegistry(NamespacedKey key) {
		@SuppressWarnings("unchecked")
		Registry<T> registry = registries.get(key);
		return registry;
	}

	@Override
	public <T> T getDefault(NamespacedKey key) {
		@SuppressWarnings("unchecked")
		Registry<T> registry = registries.get(key);
		if (registry == null)
			return null;
		return registry.getDefault();
	}

	@Override
	public <T> Registry<T> createRegistry(NamespacedKey key, Class<T> type) {
		CoreRegistry<T> registry = new CoreRegistry<>(key, type);
		registries.register(key, registry);
		return registry;
	}

	@Override
	public <T> Registry<T> getRegistry(String key) {
		@SuppressWarnings("unchecked")
		Registry<T> registry = registries.get(key);
		return registry;
	}

	@Override
	public <T> T getDefault(Class<T> clazz) {
		Registry<T> registry = getRegistry(clazz);
		if (registry == null)
			return null;
		return registry.getDefault();
	}

	@Override
	public <T> Registry<T> getRegistry(Class<T> clazz) {
		RegistryHolder holder = clazz.getAnnotation(RegistryHolder.class);
		if (holder == null)
			throw new IllegalArgumentException("Class does not contain  RegistryHolder annotation: " + clazz.getName());
		return getRegistry(holder.key());
	}

}
