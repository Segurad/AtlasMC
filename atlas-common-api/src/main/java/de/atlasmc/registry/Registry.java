package de.atlasmc.registry;

import java.util.Collection;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.Nullable;

public interface Registry<T> extends Namespaced {
	
	T getDefault();
	
	T setDefault(T defaultEntry);
	
	T get(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key, T defaultValue);
	
	boolean register(PluginHandle plugin, NamespacedKey key, T value);
	
	boolean register(PluginHandle plugin, String key, T value);
	
	boolean register(PluginHandle plugin, String[] keys, T[] values);

	@Nullable
	T get(String key);
	
	RegistryEntry<T> getEntry(String key);
	
	@Nullable
	T getOrDefault(String key);
	
	@Nullable
	T getOrDefault(String key, T defaultValue);

	@Nullable
	RegistryEntry<T> remove(String key);
	
	@Nullable
	RegistryEntry<T> remove(NamespacedKey key);
	
	int size();
	
	Target getTarget();
	
	Class<?> getType();
	
	Collection<RegistryEntry<T>> entries();
	
	Collection<T> values();
	
	Set<String> keySet();
	
	Collection<RegistryEntry<T>> getPluginEntries(PluginHandle plugin);
	
	boolean removePluginEntries(PluginHandle plugin);
	
}
