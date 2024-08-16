package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
	
	protected static final int DEFAULT_COLLECTION_CAPACITY = 3;
	
	private final Map<K, Collection<V>> map;
	private final int defaultCollectionCapacity;
	
	protected AbstractMultimap(Map<K, Collection<V>> map, int defaultCollectionCapacity) {
		this.map = map;
		this.defaultCollectionCapacity = defaultCollectionCapacity;
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}
	
	@Override
	public Collection<Collection<V>> values() {
		return map.values();
	}
	
	@Override
	public Set<Entry<K, Collection<V>>> entrySet() {
		return map.entrySet();
	}

	@Override
	public Collection<V> get(K key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Collection<V> remove(K key) {
		return map.remove(key);
	}
	
	@Override
	public V remove(K key, V value) {
		Collection<V> c = map.get(key);
		if (c == null)
			return null;
		if (c.remove(value))
			return value;
		return null;
	}

	@Override
	public boolean put(K key, V value) {
		Collection<V> c = map.get(key);
		if (c == null)
			map.put(key, c = createCollection(defaultCollectionCapacity));
		if (value == null)
			return true;
		return c.add(value);
	}
	
	@Override
	public boolean putAll(K key, Collection<V> values) {
		if (values.isEmpty())
			return false;
		Collection<V> c = map.get(key);
		if (c == null)
			c = map.put(key, createCollection(Math.max(values.size(), defaultCollectionCapacity)));
		return c.addAll(values);
	}

	@Override
	public boolean putAll(K key, Iterable<V> values) {
		Iterator<V> it = values.iterator();
		if (!it.hasNext())
			return false;
		Collection<V> c = map.get(key);
		if (c == null)
			c = map.put(key, createCollection(defaultCollectionCapacity));
		boolean b = false;
		for (V value : values) {
			if (c.add(value))
				b = true;
		}
		return b;
	}
	
	@Override
	public boolean putAll(Multimap<K, V> map) {
		if(map.isEmpty())
			return false;
		boolean b = false;
		for (K key : map.keySet()) {
			if (putAll(key, map.get(key))) b = true;
		}
		return b;
	}

	@Override
	public Map<K, Collection<V>> asMap() {
		return map;
	}
	
	@Override
	public void clear() {
		map.clear();
	}
	
	@Override
	public boolean containsValue(Object value) {
		for (Collection<V> c : map.values()) {
			if (c.contains(value)) return true;
		}
		return false;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	
	@Override
	public int size() {
		return map.size();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof Multimap))
			return false;
		Multimap<?, ?> otherMap = (Multimap<?, ?>) obj;
		return map.equals(otherMap.asMap());
	}
	
	@Override
	public int hashCode() {
		return map.hashCode();
	}
	
	protected abstract Collection<V> createCollection(int capacity);
	
}
