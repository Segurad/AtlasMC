package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public abstract class AbstractConcurrentMultimap<K, V> extends AbstractMultimap<K, V> {

	public AbstractConcurrentMultimap() {
		super(new ConcurrentHashMap<>(), DEFAULT_COLLECTION_CAPACITY);
	}
	
	public AbstractConcurrentMultimap(int mapCapacity) {
		this(mapCapacity, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public AbstractConcurrentMultimap(Map<K, Collection<V>> map) {
		this(map, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public AbstractConcurrentMultimap(Map<K, Collection<V>> map, int defaultCollectionCapacity) {
		super(new ConcurrentHashMap<>(map.size()), defaultCollectionCapacity);
		for (K key : map.keySet()) {
			putAll(key, map.get(key));
		}
	}
	
	public AbstractConcurrentMultimap(int mapCapacity, int defaultCollectionCapacity) {
		super(new ConcurrentHashMap<>(mapCapacity), defaultCollectionCapacity);
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
	public synchronized boolean putAll(K key, Collection<V> values) {
		return super.putAll(key, values);
	}

}
