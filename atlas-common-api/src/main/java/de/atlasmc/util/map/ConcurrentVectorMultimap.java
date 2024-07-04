package de.atlasmc.util.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public class ConcurrentVectorMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V> {
	
	public ConcurrentVectorMultimap() {
		this(new ConcurrentHashMap<>());
	}
	
	public ConcurrentVectorMultimap(Map<K, Collection<V>> map) {
		this(map, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public ConcurrentVectorMultimap(Map<K, Collection<V>> map, int defaultCollctionCapacity) {
		super(new HashMap<>(), defaultCollctionCapacity);
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public ConcurrentVectorMultimap(int mapCapacity, int defaultCollectionCapacity) {
		super(new HashMap<>(mapCapacity), defaultCollectionCapacity);
	}
	
	@Override
	public synchronized boolean put(K key, V value) {
		return super.put(key, value);
	}
	
	@Override
	public synchronized boolean putAll(K key, Iterable<V> values) {
		return super.putAll(key, values);
	}

	@Override
	public Vector<V> get(K key) {
		return (Vector<V>) super.get(key);
	}

	@Override
	public Vector<V> remove(K key) {
		return (Vector<V>) super.remove(key);
	}

	@Override
	protected Collection<V> createCollection(int capacity) {
		return new Vector<>(capacity);
	}

}
