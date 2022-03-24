package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
	
	private final Map<K, Collection<V>> map;
	
	protected AbstractMultimap(Map<K, Collection<V>> map) {
		this.map = map;
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
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
		return map.remove(map);
	}

	@Override
	public boolean put(K key, V value) {
		Collection<V> c;
		if (!map.containsKey(key)) {
			c = map.put(key, createCollection());
		} else c = map.get(key);
		return c.add(value);
	}

	@Override
	public boolean putAll(K key, Iterable<V> values) {
		Collection<V> c;
		if (!map.containsKey(key)) {
			c = map.put(key, createCollection());
		} else c = map.get(key);
		boolean b = false;
		Iterator<V> iterator = values.iterator();
		while (iterator.hasNext()) {
			if (c.add(iterator.next())) b = true;
		}
		return b;
	}
	
	@Override
	public boolean putAll(Multimap<K, V> map) {
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

	protected abstract Collection<V> createCollection();
}
