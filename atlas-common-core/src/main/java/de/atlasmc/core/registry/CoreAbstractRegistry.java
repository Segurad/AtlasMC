package de.atlasmc.core.registry;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.atlasmc.NamespacedKey;
import de.atlasmc.plugin.PluginHandle;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryEntry;

public abstract class CoreAbstractRegistry<T> implements Registry<T> {

	protected final Map<PluginHandle, Collection<RegistryEntry<T>>> pluginEntries;
	protected final Map<String, RegistryEntry<T>> entries;
	protected final NamespacedKey key;
	private volatile T defaultEntry;
	protected final Class<?> type;
	private Values values;
	protected final Lock modifyLock = new ReentrantLock();
	
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
	public T get(CharSequence key) {
		return getOrDefault(key, null);
	}
	
	@Override
	public T getOrDefault(CharSequence key) {
		return getOrDefault(key, this.defaultEntry);
	}
	
	@Override
	public T getOrDefault(CharSequence key, T defaultValue) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		RegistryEntry<T> value = entries.get(key.toString());
		return value != null ? value.value() : defaultValue;
	}

	@Override
	public RegistryEntry<T> register(PluginHandle plugin, CharSequence key, T value) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return internalRegister(plugin, key.toString(), value);
	}
	
	protected RegistryEntry<T> internalRegister(PluginHandle plugin, String key, T value) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		validateEntry(value);
		RegistryEntry<T> entry = createEntry(plugin, key, value);
		RegistryEntry<T> old;
		modifyLock.lock();
		old = entries.put(key, entry);
		if (old != null) {
			Collection<RegistryEntry<T>> entries = pluginEntries(old.plugin());
			entries.remove(old);
			if (entries.isEmpty())
				pluginEntries.remove(old.plugin(), entries);
			onRemove(old);
		}
		Collection<RegistryEntry<T>> entries = pluginEntries(plugin);
		entries.add(entry);
		onAdd(entry);
		modifyLock.unlock();
		return old;
	}
	
	protected RegistryEntry<T> createEntry(PluginHandle plugin, String key, T value) {
		return new CoreRegistryEntry<T>(plugin, key, value);
	}
	
	private Collection<RegistryEntry<T>> pluginEntries(PluginHandle plugin) {
		Collection<RegistryEntry<T>> entries = pluginEntries.get(plugin);
		if (entries != null)
			return entries;
		modifyLock.lock();
		entries = pluginEntries.get(plugin);
		if (entries != null)
			return entries;
		entries = new CopyOnWriteArrayList<>();
		pluginEntries.put(plugin, entries);
		modifyLock.unlock();
		return entries;
	}
	
	protected abstract void validateEntry(T value);
	
	@Override
	public RegistryEntry<T> getEntry(CharSequence key) {
		return entries.get(key);
	}
	
	@Override
	public int size() {
		return entries.size();
	}
	
	@Override
	public T setDefault(T defaultEntry) {
		modifyLock.lock();
		T old = this.defaultEntry;
		this.defaultEntry = defaultEntry;
		modifyLock.unlock();
		return old;
	}

	@Override
	public Class<?> getType() {
		return type;
	}
	
	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet(entries.keySet());
	}
	
	@Override
	public Collection<RegistryEntry<T>> entries() {
		return Collections.unmodifiableCollection(entries.values());
	}
	
	@Override
	public Collection<T> values() {
		Values values = this.values;
		if (values == null)
			values = this.values = new Values();
		return values;
	}
	
	@Override
	public RegistryEntry<T> remove(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		modifyLock.lock();
		RegistryEntry<T> entry = entries.remove(key);
		if (entry == null) {
			modifyLock.unlock();
			return null;
		}
		Collection<RegistryEntry<T>> pluginEntries = this.pluginEntries.get(entry.plugin());
		if (pluginEntries != null) {
			pluginEntries.remove(entry);
			if (pluginEntries.isEmpty())
				this.pluginEntries.remove(entry.plugin(), pluginEntries);
		}
		onRemove(entry);
		modifyLock.unlock();
		return entry;
	}
	
	/**
	 * Notify removal of entry should always be in lock context
	 * @param entry that was removed
	 */
	protected void onRemove(RegistryEntry<T> entry) {
		// override in child
	}
	
	/**
	 * Notify addition of entry should always be in lock context
	 * @param entry that was added
	 */
	protected void onAdd(RegistryEntry<T> entry) {
		// override in child
	}
	
	@Override
	public boolean removePluginEntries(PluginHandle plugin) {
		if (plugin == null)
			throw new IllegalArgumentException("Plugin can not be null!");
		modifyLock.lock();
		Collection<RegistryEntry<T>> entries = this.pluginEntries.remove(plugin);
		if (entries == null) {
			modifyLock.unlock();
			return false;
		}
		boolean changes = false;
		for (RegistryEntry<T> entry : entries) {
			if (!this.entries.remove(entry.key(), entries))
				continue;
			changes = true;
			onRemove(entry);
		}
		entries.clear();
		modifyLock.unlock();
		return changes;
	}
	
	@Override
	public Set<PluginHandle> getHandles() {
		return Collections.unmodifiableSet(pluginEntries.keySet());
	}
	
	@Override
	public Collection<RegistryEntry<T>> getPluginEntries(PluginHandle plugin) {
		return Collections.unmodifiableCollection(this.pluginEntries.get(plugin));
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
	
	@Override
	public boolean containsKey(CharSequence key) {
		return entries.containsKey(key);
	}
	
	protected static class CoreRegistryEntry<T> implements RegistryEntry<T> {
		
		public final PluginHandle plugin;
		public final String key;
		public final T value;
		
		public CoreRegistryEntry(PluginHandle plugin, String key, T value) {
			this.plugin = plugin;
			this.key = key;
			this.value = value;
		}

		@Override
		public T value() {
			return value;
		}

		@Override
		public PluginHandle plugin() {
			return plugin;
		}

		@Override
		public String key() {
			return key;
		}
		
	}

}
