package de.atlascore.registry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.NamespacedKey;
import de.atlasmc.registry.Registry;

public abstract class CoreAbstractRegistry<T> implements Registry<T> {

	protected final Map<String, T> entries;
	protected final NamespacedKey key;
	private volatile T defaultEntry;
	protected final Class<?> type;
	
	public CoreAbstractRegistry(NamespacedKey key, Class<?> type) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.key = key;
		this.type = type;
		this.entries = new ConcurrentHashMap<>();
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public T getDefault() {
		return defaultEntry;
	}

	@Override
	public T get(NamespacedKey key) {
		return getOrDefault(key, null);
	}
	
	@Override
	public T getOrDefault(NamespacedKey key) {
		return getOrDefault(key, this.defaultEntry);
	}
	
	@Override
	public T getOrDefault(NamespacedKey key, T defaultValue) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		T value = entries.get(key.toString());
		return value != null ? value : defaultValue;
	}

	@Override
	public boolean register(NamespacedKey key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		validateEntry(value);
		entries.put(key.toString(), value);
		return true;
	}

	@Override
	public boolean register(String key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!NamespacedKey.NAMESPACED_KEY_PATTERN.matcher(key).matches())
			throw new IllegalArgumentException("Key must be a valid namespaced key: " + key);
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		validateEntry(value);
		entries.put(key, value);
		return true;
	}
	
	protected abstract void validateEntry(T value);

	@Override
	public T get(String key) {
		return getOrDefault(key, null);
	}
	
	@Override
	public T getOrDefault(String key) {
		return getOrDefault(key, this.defaultEntry);
	}
	
	@Override
	public T getOrDefault(String key, T defaultValue) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		T value = entries.get(key);
		return value != null ? value : defaultValue;
	}
	
	@Override
	public int size() {
		return entries.size();
	}
	
	@Override
	public synchronized T setDefault(T defaultEntry) {
		T old = this.defaultEntry;
		this.defaultEntry = defaultEntry;
		return old;
	}

	@Override
	public Class<?> getType() {
		return type;
	}
	
	@Override
	public Set<Entry<String, T>> entrySet() {
		return entries.entrySet();
	}
	
	@Override
	public Set<String> keySet() {
		return entries.keySet();
	}
	
	@Override
	public Collection<T> values() {
		return entries.values();
	}

}
