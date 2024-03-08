package de.atlasmc.registry;

import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;

public class Registries {
	
	private static RegistryHandler HANDLER;
	public static final String DEFAULT_REGISTRY_KEY = "registry:default";
	
	private Registries() {}
	
	public static <T> T getInstanceDefault(Class<T> clazz) {
		return HANDLER.getInstanceDefault(clazz);
	}
	
	public static <T> Class<? extends T> getClassDefault(Class<T> clazz) {
		return HANDLER.getClassDefault(clazz);
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

	public static <T> InstanceRegistry<T> getInstanceRegistry(Class<T> clazz) {
		return HANDLER.getInstanceRegistry(clazz);
	}
	
	public static <T> ClassRegistry<? extends T> getClassRegistry(Class<T> clazz) {
		return HANDLER.getClassRegistry(clazz);
	}
	
	public static <T> InstanceRegistry<T> createInstanceRegistry(NamespacedKey key, Class<T> clazz) {
		return HANDLER.createInstanceRegistry(key, clazz);
	}
	
	public static <T> ClassRegistry<T> createClassRegistry(NamespacedKey key, Class<T> clazz) {
		return HANDLER.createClassRegistry(key, clazz);
	}
	
	public static <T> Registry<T> createRegistry(NamespacedKey key, Class<?> clazz, Target target) {
		return HANDLER.createRegistry(key, clazz, target);
	}
	
	public static <T> InstanceRegistry<T> createInstanceRegistry(Class<T> clazz) {
		return HANDLER.createInstanceRegistry(clazz);
	}
	
	public static <T> ClassRegistry<T> createClassRegistry(Class<T> clazz) {
		return HANDLER.createClassRegistry(clazz);
	}
	
	public static <T> Registry<T> createRegistry(Class<?> clazz, Target target) {
		return HANDLER.createRegistry(clazz, target);
	}
	
	public static void registerRegistry(Registry<?> registry) {
		HANDLER.registerRegistry(registry);
	}
	
	@NotNull
	public static RegistryHandler getHandler() {
		return HANDLER;
	}
	
	@NotNull
	public static Collection<Registry<?>> getRegistries() {
		return HANDLER.getRegistries();
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
