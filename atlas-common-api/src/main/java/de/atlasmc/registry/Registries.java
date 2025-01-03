package de.atlasmc.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.ToIntFunction;

import com.google.gson.stream.JsonReader;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.ProtocolRegistry.Deserializer;
import de.atlasmc.registry.ProtocolRegistry.Serializer;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.configuration.Configuration;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import de.atlasmc.util.factory.FactoryException;

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
	
	public static <T> void loadBulkRegistryEntries(Registry<T> registry, PluginHandle plugin, String resource) throws IOException {
		if (registry == null)
			throw new IllegalArgumentException("Registry can not be null!");
		if (plugin == null)
			throw new IllegalArgumentException("Plugincan not be null!");
		if (resource == null)
			throw new IllegalArgumentException("Resource can not be null!");
		InputStream in = plugin.getPlugin().getResourceAsStream(resource);
		if (in == null)
			throw new IllegalArgumentException("No resource found: " + resource);
		JsonReader reader = new JsonReader(new InputStreamReader(in));
		Class<?> registryType = registry.getType();
		reader.beginArray();
		while (reader.hasNext()) {
			JsonConfiguration config = JsonConfiguration.loadConfiguration(reader);
			Class<T> typeClass;
			String rawType = config.getString("type");
			try {
				@SuppressWarnings("unchecked")
				Class<T> clazz = (Class<T>) Class.forName(rawType);
				typeClass = clazz;
			} catch (ClassNotFoundException e) {
				throw new FactoryException("Error while fetching class: " + rawType);
			}
			if (!registryType.isAssignableFrom(typeClass))
				throw new FactoryException("Given type (" + rawType + ") is not compatiple with registry: " + registry.getNamespacedKeyRaw());
			Constructor<T> constructor = null;
			try {
				constructor = typeClass.getConstructor(Configuration.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new FactoryException("Error while fetching constructor!", e);
			}
			List<ConfigurationSection> configurations = config.getConfigurationList("configurations");
			for (ConfigurationSection cfg : configurations) {
				String name = cfg.getString("name");
				boolean isDefault = cfg.getBoolean("default", false);
				ConfigurationSection params = cfg.getConfigurationSection("parameters");
				T entry;
				try {
					entry = constructor.newInstance(params);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new FactoryException("Error while instaciating entry: " + name, e);
				}
				if (name == null) {
					if (entry instanceof Namespaced keyed) {
						registry.register(plugin, keyed.getNamespacedKey(), entry);
					} else {
						throw new FactoryException("No name specified and entry is not instance of Namespaced!");
					}
				} else {
					registry.register(plugin, name, entry);
				}
				if (isDefault)
					registry.setDefault(entry);
			}
		}
		reader.endArray();
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
