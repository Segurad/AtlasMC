package de.atlascore.registry;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.ClassRegistry;
import de.atlasmc.registry.InstanceRegistry;
import de.atlasmc.registry.Registries;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHandler;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.configuration.ConfigurationSerializeable;
import de.atlasmc.util.configuration.file.YamlConfiguration;

public class CoreRegistryHandler implements RegistryHandler {
	
	@SuppressWarnings("rawtypes")
	private final Registry<Registry> registries;
	
	public CoreRegistryHandler() {
		registries = new CoreInstanceRegistry<>(NamespacedKey.of(NamespacedKey.ATLAS, "registry_root"), Registry.class);
		registries.register(CorePluginManager.SYSTEM, registries.getNamespacedKey(), registries);
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
		registries.register(null, key, registry);
		return registry;
	}
	
	@Override
	public <T> ClassRegistry<T> createClassRegistry(NamespacedKey key, Class<T> clazz) {
		ClassRegistry<T> registry = new CoreClassRegistry<>(key, clazz);
		registries.register(null, key, registry);
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
		registries.register(CorePluginManager.SYSTEM, key, registry);
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
		this.registries.register(CorePluginManager.SYSTEM, registry.getKey(), registry);
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

	@Override
	public boolean removePluginEntries(PluginHandle plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		boolean changes = false;
		for (@SuppressWarnings("rawtypes") Registry reg : registries.values()) {
			if (reg.removePluginEntries(plugin))
				changes = true;
		}
		return changes;
	}

	@Override
	public void loadRegistries(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		InputStream in = plugin.getResourceAsStream("/META-INF/atlas/registries.yml");
		if (in == null)
			return;
		Log logger = plugin.getLogger();
		logger.info("Loading registries...");
		YamlConfiguration registryConfig = null;
		try {
			registryConfig = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			logger.error("Error while loading registries", e);
			return;
		}
		loadRegistries(plugin, registryConfig);
	}

	@Override
	public void loadRegistryEntries(Plugin plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		InputStream in = plugin.getResourceAsStream("/META-INF/atlas/registry-entries.yml");
		if (in == null)
			return;
		Log logger = plugin.getLogger();
		logger.info("Loading registry entries...");
		YamlConfiguration registryEntryConfig = null;
		try {
			registryEntryConfig = YamlConfiguration.loadConfiguration(in);
		} catch(IOException e) {
			logger.error("Error while loading registry entries!", e);
			return;
		}
		loadRegistryEntries(plugin, registryEntryConfig);
	}

	@Override
	public void loadRegistries(PluginHandle plugin, ConfigurationSection config) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Configuration can not be null!");
		Log logger = plugin.getPlugin().getLogger();
		for (String key : config.getKeys()) {
			ConfigurationSection regCfg = config.getConfigurationSection(key);
			String rawClass = regCfg.getString("type");
			Target target = Target.valueOf(regCfg.getString("target"));
			try {
				Class<?> registryType = Class.forName(rawClass);
				createRegistry(NamespacedKey.of(key), registryType, target);
				logger.debug("Created registry ({}) of type: {}", key, rawClass);
			} catch (ClassNotFoundException e) {
				logger.error("Registry ({}) class not found: {}", key, rawClass);
				continue;
			}
		}
	}

	@Override
	public void loadRegistryEntries(PluginHandle plugin, ConfigurationSection config) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Configuration can not be null!");
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
					entryClass = Class.forName(rawClass);
				} catch (ClassNotFoundException e) {
					logger.error("Registry (" + key + ") entry class not found: " + rawClass, e);
					continue;
				}
				Object entryValue = null;
				if (registry.getTarget() == Target.CLASS) {
					entryValue = entryClass;
				} else {
					ConfigurationSection cfg = entryCfg.getConfigurationSection("configuration");
					if (cfg != null && entryClass.isAssignableFrom(ConfigurationSerializeable.class)) {
						try {
							entryValue = entryClass.getConstructor(ConfigurationSection.class).newInstance(cfg);
						} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
								| InvocationTargetException | NoSuchMethodException | SecurityException e) {
							logger.error("Unable to create instance of registry entry using configuration: " + entryClass.getName(), e);
						}
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
				} else if (registry.register(plugin, entryKey, entryValue)) {
					logger.debug("Registry ({}) registered: {}={}", key, entryKey, entryClass.getName());
				} else {
					logger.warn("Registry ({}) tried to register value twice {}={}", key, entryKey, entryClass.getName());
				}
			}
		}
	}

}
