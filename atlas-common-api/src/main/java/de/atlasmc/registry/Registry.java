package de.atlasmc.registry;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.RegistryHolder.Target;
import de.atlasmc.util.annotation.Nullable;

public interface Registry<T> extends Namespaced {
	
	T getDefault();
	
	T setDefault(T defaultEntry);
	
	T get(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key);
	
	T getOrDefault(NamespacedKey key, T defaultValue);
	
	boolean register(NamespacedKey key, T value);
	
	boolean register(String key, T value);

	@Nullable
	T get(String key);
	
	@Nullable
	T getOrDefault(String key);
	
	@Nullable
	T getOrDefault(String key, T defaultValue);

	T remove(String key);
	
	T remove(NamespacedKey key);
	
	int size();
	
	Target getTarget();
	
	Class<?> getType();

	Collection<T> values();
	
	Set<Entry<String, T>> entrySet();
	
	Set<String> keySet();
	
}
