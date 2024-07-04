package de.atlascore.registry;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.ClassRegistry;
import de.atlasmc.registry.InstanceRegistry;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHandler;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;

public class CoreRegistryHandler implements RegistryHandler {
	
	@SuppressWarnings("rawtypes")
	private final Registry<Registry> registries;
	
	public CoreRegistryHandler() {
		registries = new CoreInstanceRegistry<>(NamespacedKey.of(NamespacedKey.ATLAS, "registry_root"), Registry.class);
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
	public <T> InstanceRegistry<T> createInstanceRegistry(NamespacedKey key, Class<T> clazz) {
		InstanceRegistry<T> registry = new CoreInstanceRegistry<>(key, clazz);
		registries.register(key, registry);
		return registry;
	}
	
	@Override
	public <T> ClassRegistry<T> createClassRegistry(NamespacedKey key, Class<T> clazz) {
		ClassRegistry<T> registry = new CoreClassRegistry<>(key, clazz);
		registries.register(key, registry);
		return registry;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Registry<T> createRegistry(NamespacedKey key, Class<?> clazz, Target target) {
		Registry<?> registry = null;
		switch (target) {
		case INSTANCE: {
			registry = createInstanceRegistry(key, clazz);
			break;
		}
		case CLASS:
			registry = new CoreClassRegistry<>(key, clazz);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + target);
		}
		registries.register(key, registry);
		return (Registry<T>) registry;
	}

	@Override
	public <T> Registry<T> getRegistry(String key) {
		@SuppressWarnings("unchecked")
		Registry<T> registry = registries.get(key);
		return registry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> InstanceRegistry<T> getInstanceRegistry(Class<T> clazz) {
		Registry<?> registry = getRegistry(clazz);
		if (registry instanceof InstanceRegistry reg)
			return reg;
		return null;
	}
	
	@Override
	public Registry<?> getRegistry(Class<?> clazz) {
		return getRegistry(getRegistryKey(clazz));
	}
	
	private String getRegistryKey(Class<?> clazz) {
		RegistryHolder holder = clazz.getAnnotation(RegistryHolder.class);
		if (holder == null)
			throw new IllegalArgumentException("Class does not contain  RegistryHolder annotation: " + clazz.getName());
		return holder.key();
	}

	@Override
	public <T> T getInstanceDefault(Class<T> clazz) {
		InstanceRegistry<T> registry = getInstanceRegistry(clazz);
		return registry != null ? registry.getDefault() : null;
	}

	@Override
	public <T> Class<? extends T> getClassDefault(Class<T> clazz) {
		ClassRegistry<T> registry = getClassRegistry(clazz);
		return registry != null ? registry.getDefault() : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> ClassRegistry<T> getClassRegistry(Class<T> clazz) {
		Registry<?> registry = getRegistry(clazz);
		if (registry instanceof ClassRegistry reg)
			return reg;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Registry<?>> getRegistries() {
		Collection<?> registries = this.registries.values();
		return (Collection<Registry<?>>) registries;
	}

	@Override
	public void registerRegistry(Registry<?> registry) {
		this.registries.register(registry.getKey(), registry);
	}

	@Override
	public <T> InstanceRegistry<T> createInstanceRegistry(Class<T> clazz) {
		NamespacedKey key = NamespacedKey.of(getRegistryKey(clazz));
		return createInstanceRegistry(key, clazz);
	}

	@Override
	public <T> ClassRegistry<T> createClassRegistry(Class<T> clazz) {
		NamespacedKey key = NamespacedKey.of(getRegistryKey(clazz));
		return createClassRegistry(key, clazz);
	}

	@Override
	public <T> Registry<T> createRegistry(Class<?> clazz, Target target) {
		NamespacedKey key = NamespacedKey.of(getRegistryKey(clazz));
		return createRegistry(key, clazz, target);
	}

}
