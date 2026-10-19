package de.atlasmc.registry;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import com.google.gson.stream.JsonReader;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.log.Log;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializable;
import de.atlasmc.util.configuration.file.JsonConfiguration;
import de.atlasmc.util.configuration.file.JsonConfigurationFactory;
import de.atlasmc.util.configuration.file.YamlConfiguration;
import de.atlasmc.util.configuration.file.YamlConfigurationFactory;
import de.atlasmc.util.factory.FactoryException;

public class Registries {
	
	private static RegistryHandler HANDLER;
	
	public static final String DEFAULT_REGISTRY_KEY = "registry:default";
	
	private Registries() {}
	
	public static <T> T getDefault(CharSequence key) {
		return HANDLER.getDefault(key);
	}
	
	public static <T> T getDefault(Class<?> clazz) {
		return HANDLER.getDefault(clazz);
	}
	
	public static <T> RegistryKey<T> getRegistryKey(Class<T> clazz) {
		return getRegistryKey(HANDLER.getRegistryKey(clazz));
	}
	
	public static <T> RegistryKey<T> getRegistryKey(CharSequence key) {
		return new RegistryKey<>(NamespacedKey.of(key));
	}
	
	public static <T extends Registry<?>> T getRegistry(Class<?> clazz) {
		return HANDLER.getRegistry(clazz);
	}
	
	public static <T extends Registry<?>> T getRegistry(CharSequence key) {
		return HANDLER.getRegistry(key);
	}
	
	public static <T> T getValue(Class<?> registry, CharSequence key) {
		Registry<T> reg = HANDLER.getRegistry(registry);
		if (reg == null)
			return null;
		return reg.get(key);
	}
	
	public static <T> T getValue(CharSequence registry, CharSequence key) {
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
	
	public static void loadRegistries(Plugin plugin) {
		Objects.requireNonNull(plugin, "plugin");
		InputStream in = plugin.getResourceAsStream(RegistryHolderProcessor.RESOURCE_LOCATION);
		if (in == null)
			return;
		Log logger = plugin.getLogger();
		logger.info("Loading registries...");
		YamlConfiguration registryConfig;
		try {
			registryConfig = YamlConfigurationFactory.INSTANCE.load(in);
		} catch(IOException e) {
			logger.error("Error while loading registries", e);
			return;
		}
		loadRegistries(plugin, registryConfig);
	}

	public static void loadRegistryEntries(Plugin plugin) {
		Objects.requireNonNull(plugin, "plugin");
		InputStream in = plugin.getResourceAsStream(RegistryValueProcessor.RESOURCE_LOCATION);
		if (in == null)
			return;
		Log logger = plugin.getLogger();
		logger.info("Loading registry entries...");
		YamlConfiguration registryEntryConfig;
		try {
			registryEntryConfig = YamlConfigurationFactory.INSTANCE.load(in);
		} catch(IOException e) {
			logger.error("Error while loading registry entries!", e);
			return;
		}
		loadRegistryEntries(plugin, registryEntryConfig);
	}

	public static void loadRegistries(Plugin plugin, ConfigurationSection config) {
		Objects.requireNonNull(plugin, "plugin");
		Objects.requireNonNull(config, "config");
		Log logger = plugin.getPlugin().getLogger();
		for (String key : config.getKeys()) {
			ConfigurationSection regCfg = config.getConfigurationSection(key);
			String rawClass = regCfg.getString("type");
			Target target = Target.valueOf(regCfg.getString("target")); // implicit null and validity check
			try {
				Class<?> registryType = Class.forName(rawClass, true, plugin.getPlugin().getClass().getClassLoader());
				createRegistry(NamespacedKey.of(key), registryType, target);
				logger.debug("Created registry ({}) of type: {}", key, rawClass);
			} catch (ClassNotFoundException e) {
				logger.error("Registry ({}) class not found: {}", key, rawClass);
				continue;
			}
		}
	}

	public static void loadRegistryEntries(PluginHandle plugin, ConfigurationSection config) {
		Objects.requireNonNull(plugin, "plugin");
		Objects.requireNonNull(config, "config");
		Log logger = plugin.getPlugin().getLogger();
		for (String key : config.getKeys()) {
			Registry<Object> registry = getRegistry(key);
			if (registry == null) {
				logger.warn("Unable to insert registry entries for registry: {} (missing)", key);
				continue;
			}
			ConfigurationSection entries = config.getConfigurationSection(key);
			for (String entryKey : entries.getKeys()) {
				ConfigurationSection entryCfg = entries.getConfigurationSection(entryKey);
				String rawClass = entryCfg.getString("type");
				Class<?> entryClass = null;
				try {
					entryClass = Class.forName(rawClass, true, plugin.getPlugin().getClass().getClassLoader());
				} catch (ClassNotFoundException e) {
					logger.error("Registry (" + key + ") entry class not found: " + rawClass, e);
					continue;
				}
				Object entryValue = null;
				if (registry.getTarget() == Target.CLASS) {
					entryValue = entryClass;
				} else {
					ConfigurationSection cfg = entryCfg.getConfigurationSection("configuration");
					if (cfg != null && ConfigurationSerializable.class.isAssignableFrom(entryClass)) {
						try {
							entryValue = entryClass.getConstructor(ConfigurationSection.class).newInstance(cfg);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							logger.error("Unable to create instance of registry entry using configuration: " + entryClass.getName(), e);
						}
					} else if (entryClass.getDeclaredAnnotation(Singleton.class) != null) {
						try {
							var m = entryClass.getDeclaredMethod("getInstance");
							try {
								entryValue = m.invoke(null);
							} catch (IllegalAccessException | InvocationTargetException e) {
								logger.error("Unable to fetch singleton instance using getInstance: " + entryClass.getName(), e);
							}
						} catch (NoSuchMethodException e) {
							try {
								var f = entryClass.getField("INSTANCE");
								try {
									entryValue = f.get(null);
								} catch (IllegalArgumentException | IllegalAccessException e1) {
									logger.error("Unable to fetch singleton instance using INSTANCE: " + entryClass.getName(), e);
								}
							} catch (NoSuchFieldException e1) {
								// no instance field
							}
						}
						if (entryValue == null)
							logger.warn("Failed to fetch singleton instance: {}", entryClass.getName());
					} else {
						try {
							entryValue = entryClass.getConstructor().newInstance();
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							logger.error("Unable to create instance of registry entry: " + entryClass.getName(), e);
						}
					}
				}
				if (Registries.DEFAULT_REGISTRY_KEY.equals(entryKey)) {
					registry.setDefault(entryValue);
					logger.debug("Registry ({}) set default: {}", key, entryClass.getName());
				} else if (registry.register(plugin, entryKey, entryValue) == null) {
					logger.debug("Registry ({}) registered: {}={}", key, entryKey, entryClass.getName());
				} else {
					logger.warn("Registry ({}) register value twice {}={}", key, entryKey, entryClass.getName());
				}
			}
		}
	}
	
	public static void loadBulkRegistryEntries(PluginHandle plugin, String resource) throws IOException {
		Objects.requireNonNull(plugin, "plugin");
		Objects.requireNonNull(resource, "resource");
		try (InputStream in = plugin.getPlugin().getResourceAsStream(resource)) {
			if (in == null)
				throw new IllegalArgumentException("No resource found: " + resource);
			try (var reader = new JsonReader(new InputStreamReader(in))) {
				reader.beginArray();
				while (reader.hasNext()) {
					JsonConfiguration config = JsonConfigurationFactory.INSTANCE.load(reader);
					String key = config.getString("registry");
					if (key == null)
						throw new FactoryException("No registry key defined in resource: " + resource);
					Registry<Object> registry = Registries.getRegistry(key);
					if (registry == null)
						throw new FactoryException("Unable to find registry: " + key);
					Class<?> registryType = registry.getType();
					Class<?> typeClass;
					String rawType = config.getString("type");
					try {
						Class<?> clazz = Class.forName(rawType, true, plugin.getPlugin().getClass().getClassLoader());
						typeClass = clazz;
					} catch (ClassNotFoundException e) {
						throw new FactoryException("Error while fetching class: " + rawType);
					}
					if (!registryType.isAssignableFrom(typeClass))
						throw new FactoryException("Given type (" + rawType + ") is not compatiple with registry: " + registry.getNamespacedKey());
					Constructor<?> constructor = null;
					try {
						constructor = typeClass.getConstructor(ConfigurationSection.class);
					} catch (NoSuchMethodException | SecurityException e) {
						throw new FactoryException("Error while fetching constructor of type: " + typeClass, e);
					}
					List<ConfigurationSection> configurations = config.getConfigurationList("configurations");
					for (ConfigurationSection cfg : configurations) {
						String name = cfg.getString("name");
						boolean isDefault = cfg.getBoolean("default", false);
						ConfigurationSection params = cfg.getConfigurationSection("parameters");
						Object entry;
						try {
							entry = constructor.newInstance(params);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							throw new FactoryException("Error while instaciating entry: " + name + " of type: " + typeClass, e);
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
		}
	}
	
	public static <T extends Namespaced> NBTCodec<T> registryValueNBTCodec(RegistryKey<T> key) {
		return new RegistryValueNBTCodec<>(key);
	}
	
	@InternalAPI
	public static synchronized void init(RegistryHandler handler) {
		if (HANDLER != null)
			throw new IllegalStateException("Already initialized!");
		HANDLER = handler;
	}

}
