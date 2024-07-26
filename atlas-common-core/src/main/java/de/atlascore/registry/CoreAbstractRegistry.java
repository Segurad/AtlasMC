package de.atlascore.registry;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.Plugin;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryEntry;

public abstract class CoreAbstractRegistry<T> implements Registry<T> {

	protected final Map<Plugin, Collection<RegistryEntry<T>>> pluginEntries;
	protected final Map<String, RegistryEntry<T>> entries;
	protected final NamespacedKey key;
	private volatile T defaultEntry;
	protected final Class<?> type;
	private Values values;
	
	public CoreAbstractRegistry(NamespacedKey key, Class<?> type) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.key = key;
		this.type = type;
		this.entries = new ConcurrentHashMap<>();
		this.pluginEntries = new ConcurrentHashMap<>();
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
		RegistryEntry<T> value = entries.get(key.toString());
		return value != null ? value.value() : defaultValue;
	}

	@Override
	public boolean register(Plugin plugin, NamespacedKey key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		validateEntry(value);
		CoreRegistryEntry<T> entry = new CoreRegistryEntry<T>(plugin, key.toString(), value);
		if (entries.putIfAbsent(entry.key, entry) != null)
			return false;
		Collection<RegistryEntry<T>> entries = pluginEntries(plugin);
		entries.add(entry);
		return true;
	}

	@Override
	public boolean register(Plugin plugin, String key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!NamespacedKey.NAMESPACED_KEY_PATTERN.matcher(key).matches())
			throw new IllegalArgumentException("Key must be a valid namespaced key: " + key);
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		validateEntry(value);
		CoreRegistryEntry<T> entry = new CoreRegistryEntry<T>(plugin, key, value);
		if (entries.putIfAbsent(entry.key, entry) != null)
			return false;
		Collection<RegistryEntry<T>> entries = pluginEntries(plugin);
		entries.add(entry);
		return true;
	}
	
	@Override
	public boolean register(Plugin plugin, final String[] keys, final T[] values) {
		if (keys == null)
			throw new IllegalArgumentException("Keys can not be null!");
		if (values == null)
			throw new IllegalArgumentException("Values can not be null!");
		if (keys.length != values.length)
			throw new IllegalArgumentException("Keys and values must be the same length");
		Matcher match = NamespacedKey.NAMESPACED_KEY_PATTERN.matcher("");
		for (int i = 0; i < keys.length; i++) {
			match.reset(keys[i]);
			if (!match.matches())
				throw new IllegalArgumentException("Key must be a valid namespaced key: " + keys[i]);
		}
		ArrayList<CoreRegistryEntry<T>> entries = new ArrayList<>(keys.length);
		for (int i = 0; i < keys.length; i++) {
			T value = values[i];
			validateEntry(value);
			String key = keys[i];
			CoreRegistryEntry<T> entry = new CoreRegistryEntry<T>(plugin, key, value);
			if (this.entries.putIfAbsent(entry.key, entry) != null)
				continue;
			entries.add(entry);
		}
		Collection<RegistryEntry<T>> pluginEntries = pluginEntries(plugin);
		pluginEntries.addAll(entries);
		return entries.size() > 0;
	}
	
	private Collection<RegistryEntry<T>> pluginEntries(Plugin plugin) {
		Collection<RegistryEntry<T>> entries = pluginEntries.get(plugin);
		if (entries == null)
			return entries;
		synchronized (this) {
			entries = pluginEntries.get(plugin);
			if (entries == null)
				return entries;
			entries = new CopyOnWriteArrayList<>();
			pluginEntries.put(plugin, entries);
			return entries;
		}
	}
	
	protected abstract void validateEntry(T value);

	@Override
	public T get(String key) {
		return getOrDefault(key, null);
	}
	
	@Override
	public RegistryEntry<T> getEntry(String key) {
		return entries.get(key);
	}
	
	@Override
	public T getOrDefault(String key) {
		return getOrDefault(key, this.defaultEntry);
	}
	
	@Override
	public T getOrDefault(String key, T defaultValue) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		RegistryEntry<T> value = entries.get(key);
		return value != null ? value.value() : defaultValue;
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
	public Set<String> keySet() {
		return entries.keySet();
	}
	
	@Override
	public Collection<RegistryEntry<T>> entries() {
		return entries.values();
	}
	
	@Override
	public Collection<T> values() {
		Values values = this.values;
		if (values == null)
			values = this.values = new Values();
		return values;
	}
	
	@Override
	public RegistryEntry<T> remove(NamespacedKey key) {
		return remove(key.toString());
	}
	
	@Override
	public RegistryEntry<T> remove(String key) {
		RegistryEntry<T> entry = entries.remove(key);
		if (entry == null)
			return null;
		Collection<RegistryEntry<T>> pluginEntries = this.pluginEntries.get(entry.plugin());
		if (pluginEntries != null)
			pluginEntries.remove(entry);
		return entry;
	}
	
	@Override
	public synchronized boolean removePluginEntries(Plugin plugin) {
		Collection<RegistryEntry<T>> entries = this.pluginEntries.remove(plugin);
		if (entries == null)
			return false;
		boolean changes = false;
		for (RegistryEntry<T> entry : entries) {
			if (this.entries.remove(entry.key(), entries))
				changes = true;
		}
		entries.clear();
		return changes;
	}
	
	@Override
	public Collection<RegistryEntry<T>> getPluginEntries(Plugin plugin) {
		return this.pluginEntries.get(plugin);
	}
	
	private final class Values extends AbstractCollection<T> {

		@Override
		public Iterator<T> iterator() {
			return new Iterator<T>() {

				private Iterator<RegistryEntry<T>> it = entries.values().iterator();
				
				@Override
				public boolean hasNext() {
					return it.hasNext();
				}

				@Override
				public T next() {
					return it.next().value();
				}
			
			};
		}

		@Override
		public int size() {
			return entries.size();
		}
		
	}
	
	protected static final class CoreRegistryEntry<T> implements RegistryEntry<T> {
		
		private final Plugin plugin;
		private final String key;
		private final T value;
		
		public CoreRegistryEntry(Plugin plugin, String key, T value) {
			this.plugin = plugin;
			this.key = key;
			this.value = value;
		}

		@Override
		public T value() {
			return value;
		}

		@Override
		public Plugin plugin() {
			return plugin;
		}

		@Override
		public String key() {
			return key;
		}
		
	}

}
