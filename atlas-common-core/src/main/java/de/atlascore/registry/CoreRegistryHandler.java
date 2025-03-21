package de.atlascore.registry;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlascore.plugin.CorePluginManager;
import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryHandler;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.registry.RegistryHolder.Target;

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
			break;
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

}
