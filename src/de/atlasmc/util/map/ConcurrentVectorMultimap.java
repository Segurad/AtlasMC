package de.atlasmc.util.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */
public class ConcurrentVectorMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V> {

	private int DEFAULT_LIST_CAPACITY;
	
	public ConcurrentVectorMultimap() {
		this(new ConcurrentHashMap<>());
	}
	
	public ConcurrentVectorMultimap(Map<K, Collection<V>> map) {
		this(map, 3);
	}
	
	public ConcurrentVectorMultimap(Map<K, Collection<V>> map, int valueInitCapacity) {
		super(new HashMap<>());
		this.DEFAULT_LIST_CAPACITY = valueInitCapacity;
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public ConcurrentVectorMultimap(int mapCapacity, int valueInitCapacity) {
		super(new HashMap<K, Collection<V>>(mapCapacity));
		this.DEFAULT_LIST_CAPACITY = valueInitCapacity;
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
	protected Collection<V> createCollection() {
		return new Vector<V>(DEFAULT_LIST_CAPACITY);
	}

}
