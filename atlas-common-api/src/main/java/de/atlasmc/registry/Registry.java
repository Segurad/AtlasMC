package de.atlasmc.registry;

import java.util.Collection;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;

public interface Registry<T> extends Namespaced {
	
	T getDefault();
	
	T setDefault(T defaultEntry);
	
	T get(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key, T defaultValue);
	
	/**
	 * Registers a new entry for this registry.
	 * Returns the previous entry with the given key if present.
	 * @param plugin to associate with
	 * @param key of the entry
	 * @param value of the entry
	 * @return previous or null
	 */
	@Nullable
	RegistryEntry<T> register(PluginHandle plugin, NamespacedKey key, T value);
	
	/**
	 * Registers a new entry for this registry.
	 * Returns the previous entry with the given key if present.
	 * @param plugin to associate with
	 * @param key of the entry
	 * @param value of the entry
	 * @return previous or null
	 */
	@Nullable
	RegistryEntry<T> register(PluginHandle plugin, String key, T value);

	@Nullable
	T get(String key);
	
	@Nullable
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
	
	@NotNull
	Target getTarget();
	
	@NotNull
	Class<?> getType();
	
	@NotNull
	Collection<RegistryEntry<T>> entries();
	
	@NotNull
	Collection<T> values();
	
	@NotNull
	Set<String> keySet();
	
	@NotNull
	Set<PluginHandle> getHandles();
	
	@NotNull
	Collection<RegistryEntry<T>> getPluginEntries(PluginHandle plugin);
	
	boolean removePluginEntries(PluginHandle plugin);
	
}
