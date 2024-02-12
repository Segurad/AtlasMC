package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;

public interface RegistryHandler {

	<T> Registry<T> getRegistry(NamespacedKey key);

	<T> T getDefault(NamespacedKey key);
	
	<T> T getDefault(Class<T> clazz);
	
	<T> Registry<T> createRegistry(NamespacedKey key, Class<T> clazz);

	<T> Registry<T> getRegistry(String key);
	
	/**
	 * Returns the registry defined by the {@link RegistryHolder} of the given class or null if no registry is present
	 * @param <T>
	 * @param clazz
	 * @return registry or null
	 */
	<T> Registry<T> getRegistry(Class<T> clazz);

}
