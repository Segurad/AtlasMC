package de.atlasmc.registry;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.InternalAPI;

public class Registries {
	
	private static RegistryHandler HANDLER;
	public static final String DEFAULT_REGISTRY_KEY = "registry:default";
	
	private Registries() {}
	
	public static <T> T getDefault(Class<T> clazz) {
		return HANDLER.getDefault(clazz);
	}
	
	public static <T> T getDefault(NamespacedKey key) {
		return HANDLER.getDefault(key);
	}
	
	public static <T> Registry<T> getRegistry(NamespacedKey key) {
		return HANDLER.getRegistry(key);
	}
	
	public static <T> Registry<T> getRegistry(String key) {
		return HANDLER.getRegistry(key);
	}

	public static <T> Registry<T> getRegistry(Class<T> clazz) {
		return HANDLER.getRegistry(clazz);
	}
	
	public static <T> Registry<T> createRegistry(NamespacedKey key, Class<T> clazz) {
		return HANDLER.createRegistry(key, clazz);
	}
	
	public static RegistryHandler getHandler() {
		return HANDLER;
	}
	
	@InternalAPI
	public static void init(RegistryHandler handler) {
		if (HANDLER != null)
			throw new IllegalStateException("Already initialized!");
		synchronized (Registries.class) {
			if (HANDLER != null)
				throw new IllegalStateException("Already initialized!");
			HANDLER = handler;
		}
	}

}
