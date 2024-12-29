package de.atlascore.registry;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.NamespacedKey;
import de.atlasmc.log.Log;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.plugin.PluginHandle;
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
	private final Lock modifyLock = new ReentrantLock();
	
	public CoreRegistryHandler() {
		registries = new CoreInstanceRegistry<>(NamespacedKey.literal("atlas:registry_root"), Registry.class);
		registries.register(CorePluginManager.SYSTEM, registries.getNamespacedKey(), registries);
	}

	@Override
	public <T extends Registry<?>> T getRegistry(NamespacedKey key) {
		@SuppressWarnings("unchecked")
		T registry = (T) registries.get(key);
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
	public <T> T getDefault(String key) {	
		@SuppressWarnings("unchecked")
		Registry<T> registry = registries.get(key);
		if (registry == null)
			return null;
		return registry.getDefault();
	}
	
	@Override
	public <T> T getDefault(Class<?> clazz) {
		return getDefault(getRegistryKey(clazz));
	}
	
	@Override
	public <T extends Registry<?>> T createRegistry(NamespacedKey key, Class<?> clazz, Target target) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		if (target == null)
			throw new IllegalArgumentException("Target can not be null!");
		Registry<?> rawRegistry = checkPresentRegistry(key, clazz, target);
		if (rawRegistry != null) {
			@SuppressWarnings("unchecked")
			T registry = (T) rawRegistry;
			return registry;
		}
		modifyLock.lock();
		try {
			rawRegistry = checkPresentRegistry(key, clazz, target);
			if (rawRegistry != null) {
				modifyLock.unlock();
				@SuppressWarnings("unchecked")
				T registry = (T) rawRegistry;
				return registry;
			}
		} catch(Exception e) {
			modifyLock.unlock();
			throw e;
		}
		switch (target) {
		case INSTANCE:
			rawRegistry = new CoreInstanceRegistry<>(key, clazz);
			break;
		case CLASS:
			rawRegistry = new CoreClassRegistry<>(key, clazz);
			break;
		case PROTOCOL:
			rawRegistry = new CoreProtocolRegistry<>(key, clazz);
		default:
			modifyLock.unlock();
			throw new IllegalStateException("Unhandled target value: " + target);
		}
		registries.register(CorePluginManager.SYSTEM, key, rawRegistry);
		modifyLock.unlock();
		@SuppressWarnings("unchecked")
		T registry = (T) rawRegistry;
		return registry;
	}
	
	private Registry<?> checkPresentRegistry(NamespacedKey key, Class<?> clazz, Target target) {
		Registry<?> registry = getRegistry(key);
		if (registry == null)
			return null;
		if (registry.getTarget() != target)
			throw new IllegalStateException("Registry with different target already present: " + key + " | given [" + target + "] present [" + registry.getTarget() + "]");
		if (!registry.getType().equals(clazz))
			throw new IllegalStateException("Registry with different type already present: " + key + " | given [" + clazz.getName() + "] present [" + registry.getType().getName() + "]");
		return registry;
	}

	@Override
	public <T extends Registry<?>> T getRegistry(String key) {
		@SuppressWarnings("unchecked")
		T registry = (T) registries.get(key);
		return registry;
	}
	
	@Override
	public <T extends Registry<?>> T getRegistry(Class<?> clazz) {
		@SuppressWarnings("unchecked")
		T registry = (T) getRegistry(getRegistryKey(clazz));
		return registry;
	}
	
	private String getRegistryKey(Class<?> clazz) {
		RegistryHolder holder = clazz.getAnnotation(RegistryHolder.class);
		if (holder == null)
			throw new IllegalArgumentException("Class does not contain  RegistryHolder annotation: " + clazz.getName());
		return holder.key();
	}

	@Override
	public Registry<Registry<?>> getRegistries() {
		Registry<?> rawRegistry = this.registries;
		@SuppressWarnings("unchecked")
		Registry<Registry<?>> registry = (Registry<Registry<?>>) rawRegistry;
		return registry;
	}

	@Override
	public boolean registerRegistry(Registry<?> registry) {
		if (registry == null)
			throw new IllegalArgumentException("Registry can not be null!");
		NamespacedKey key = registry.getNamespacedKey();
		if (registries.get(key) != null)
			return false;
		modifyLock.lock();
		if (registries.get(key) != null) {
			modifyLock.unlock();
			return false;
		}
		this.registries.register(CorePluginManager.SYSTEM, key, registry);
		modifyLock.unlock();
		return true;
	}

	@Override
	public <T extends Registry<?>> T createRegistry(Class<?> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		RegistryHolder holder = clazz.getAnnotation(RegistryHolder.class);
		if (holder == null)
			throw new IllegalArgumentException("Class does not contain  RegistryHolder annotation: " + clazz.getName());
		NamespacedKey key = NamespacedKey.of(holder.key());
		return createRegistry(key, clazz, holder.target());
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
	public void loadRegistries(Plugin plugin, ConfigurationSection config) {
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
				} else if (registry.register(plugin, entryKey, entryValue) == null) {
					logger.debug("Registry ({}) registered: {}={}", key, entryKey, entryClass.getName());
				} else {
					logger.warn("Registry ({}) register value twice {}={}", key, entryKey, entryClass.getName());
				}
			}
		}
	}

}
