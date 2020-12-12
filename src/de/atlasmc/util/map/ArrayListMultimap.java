package de.atlasmc.util.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayListMultimap<K, V> extends AbstractMultimap<K, V> implements ListMultimap<K, V> {

	private int DEFAULT_LIST_CAPACITY = 3;
	
	public ArrayListMultimap() {
		super(new HashMap<>());
	}
	
	public ArrayListMultimap(Map<K, Collection<V>> map) {
		this(map, 3);
	}
	
	public ArrayListMultimap(Map<K, Collection<V>> map, int valueInitCapacity) {
		super(new HashMap<>());
		this.DEFAULT_LIST_CAPACITY = valueInitCapacity;
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public ArrayListMultimap(int mapCapacity, int valueInitCapacity) {
		super(new HashMap<K, Collection<V>>(mapCapacity));
		this.DEFAULT_LIST_CAPACITY = valueInitCapacity;
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
	protected Collection<V> createCollection() {
		return new ArrayList<V>(DEFAULT_LIST_CAPACITY);
	}

}
