package de.atlasmc.util.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V> {
	
	public ArrayListMultimap() {
		super(new HashMap<>(), DEFAULT_COLLECTION_CAPACITY);
	}
	
	public ArrayListMultimap(Map<K, Collection<V>> map) {
		this(map, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public ArrayListMultimap(Map<K, Collection<V>> map, int defaultCollectionCapacity) {
		super(new HashMap<>(), defaultCollectionCapacity);
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public ArrayListMultimap(int mapCapacity) {
		this(mapCapacity, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public ArrayListMultimap(int mapCapacity, int defaultCollectionCapacity) {
		super(new HashMap<>(mapCapacity), defaultCollectionCapacity);
	}

	@Override
	public List<V> get(K key) {
		return (List<V>) super.get(key);
	}

	@Override
	public List<V> remove(K key) {
		return (List<V>) super.remove(key);
	}

	@Override
	protected Collection<V> createCollection(int capacity) {
		return new ArrayList<>(capacity);
	}

}
