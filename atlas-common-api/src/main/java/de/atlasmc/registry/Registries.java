package de.atlasmc.registry;

import java.util.function.ToIntFunction;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.ProtocolRegistry.Deserializer;
import de.atlasmc.registry.ProtocolRegistry.Serializer;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.ConfigurationSection;

public class Registries {
	
	private static RegistryHandler HANDLER;
	public static final String DEFAULT_REGISTRY_KEY = "registry:default";
	
	private Registries() {}
	
	public static <T> T getDefault(NamespacedKey key) {
		return HANDLER.getDefault(key);
	}
	
	public static <T> T getDefault(String key) {
		return HANDLER.getDefault(key);
	}
	
	public static <T> T getDefault(Class<?> clazz) {
		return HANDLER.getDefault(clazz);
	}
	
	public static <T extends Registry<?>> T getRegistry(Class<?> clazz) {
		return HANDLER.getRegistry(clazz);
	}
	
	public static <T extends Registry<?>> T getRegistry(NamespacedKey key) {
		return HANDLER.getRegistry(key);
	}
	
	public static <T extends Registry<?>> T getRegistry(String key) {
		return HANDLER.getRegistry(key);
	}
	
	public static <T> T getValue(Class<?> registry, NamespacedKey key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(NamespacedKey registry, NamespacedKey key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(String registry, NamespacedKey key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(Class<?> registry, String key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(NamespacedKey registry, String key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(String registry, String key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	@NotNull
	public static <T extends Registry<?>> T createRegistry(NamespacedKey key, Class<?> clazz, Target target) {
		return HANDLER.createRegistry(key, clazz, target);
	}
	
	@NotNull
	public static <T extends Registry<?>> T createRegistry(Class<?> clazz) {
		return HANDLER.createRegistry(clazz);
	}
	
	public static <T> ProtocolRegistry<T> createRegistry(NamespacedKey key, Class<?> clazz, ToIntFunction<T> idSupplier, Deserializer<T> deserializer, Serializer<T> serializer) {
		ProtocolRegistry<T> registry = createRegistry(key, clazz, Target.PROTOCOL);
		registry.setIDSupplier(idSupplier);
		registry.setDeserializer(deserializer);
		registry.setSerializer(serializer);
		return registry;
	}
	
	public static <T> ProtocolRegistry<T> createRegistry(Class<?> clazz, ToIntFunction<T> idSupplier, Deserializer<T> deserializer, Serializer<T> serializer) {
		ProtocolRegistry<T> registry = createRegistry(clazz);
		registry.setIDSupplier(idSupplier);
		registry.setDeserializer(deserializer);
		registry.setSerializer(serializer);
		return registry;
	}
	
	public static boolean registerRegistry(Registry<?> registry) {
		return HANDLER.registerRegistry(registry);
	}
	
	@NotNull
	public static RegistryHandler getHandler() {
		return HANDLER;
	}
	
	@NotNull
	public static Registry<Registry<?>> getRegistries() {
		return HANDLER.getRegistries();
	}
	
	/**
	 * @see RegistryHandler#loadRegistries(Plugin)
	 */
	public static void loadRegistries(Plugin plugin) {
		HANDLER.loadRegistries(plugin);
	}
	
	public static void loadRegistries(Plugin plugin, ConfigurationSection config) {
		HANDLER.loadRegistries(plugin, config);
	}
	
	/**
	 * @see RegistryHandler#loadRegistryEntries(Plugin)
	 */
	public static void loadRegistryEntries(Plugin plugin) {
		HANDLER.loadRegistryEntries(plugin);
	}
	
	public static void loadRegistryEntries(PluginHandle plugin, ConfigurationSection config) {
		HANDLER.loadRegistryEntries(plugin, config);
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
