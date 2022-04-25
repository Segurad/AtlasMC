package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.ConcurrentLinkedList;

public class ConcurrentLinkedListMultimap<K, V> extends AbstractMultimap<K, V> {
	
	public ConcurrentLinkedListMultimap() {
		super(new ConcurrentHashMap<>());
	}
	
	public ConcurrentLinkedListMultimap(Map<K, Collection<V>> map) {
		super(new ConcurrentHashMap<>(map.size()));
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public ConcurrentLinkedListMultimap(int mapCapacity) {
		super(new ConcurrentHashMap<K, Collection<V>>(mapCapacity));
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
	public ConcurrentLinkedList<V> get(K key) {
		return (ConcurrentLinkedList<V>) super.get(key);
	}

	@Override
	public ConcurrentLinkedList<V> remove(K key) {
		return (ConcurrentLinkedList<V>) super.remove(key);
	}

	@Override
	protected Collection<V> createCollection() {
		return new ConcurrentLinkedList<V>();
	}

}
