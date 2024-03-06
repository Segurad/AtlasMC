package de.atlasmc.registry;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.RegistryHolder.Target;

public interface RegistryHandler {

	<T> Registry<T> getRegistry(NamespacedKey key);

	<T> T getDefault(NamespacedKey key);
	
	<T> T getInstanceDefault(Class<T> clazz);
	
	<T> Class<? extends T> getClassDefault(Class<T> clazz);
	
	<T> InstanceRegistry<T> createInstanceRegistry(NamespacedKey key, Class<T> clazz);
	
	<T> ClassRegistry<T> createClassRegistry(NamespacedKey key, Class<T> clazz);

	<T> Registry<T> createRegistry(NamespacedKey key, Class<?> clazz, Target target);
	
	<T> Registry<T> getRegistry(String key);
	
	/**
	 * Returns the registry defined by the {@link RegistryHolder} of the given class or null if no registry is present
	 * @param <T>
	 * @param clazz
	 * @return registry or null
	 */
	<T> InstanceRegistry<T> getInstanceRegistry(Class<T> clazz);
	
	<T> ClassRegistry<? extends T> getClassRegistry(Class<T> clazz);
	
	Registry<?> getRegistry(Class<?> clazz);

	Collection<Registry<?>> getRegistries();

	void registerRegistry(Registry<?> registry);

}
