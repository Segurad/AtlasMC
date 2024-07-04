package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public class CopyOnWriteArrayListMultimap<K, V> extends AbstractConcurrentMultimap<K, V> {

	public CopyOnWriteArrayListMultimap() {
		super();
	}
	
	public CopyOnWriteArrayListMultimap(Map<K, Collection<V>> map) {
		super(map);
	}
	
	public CopyOnWriteArrayListMultimap(Map<K, Collection<V>> map, int defaultCollectionCapacity) {
		super(map, defaultCollectionCapacity);
	}
	
	public CopyOnWriteArrayListMultimap(int mapCapacity) {
		super(mapCapacity);
	}
	
	public CopyOnWriteArrayListMultimap(int mapCapacity, int defaultCollectionCapacity) {
		super(mapCapacity, defaultCollectionCapacity);
	}

	@Override
	public ConcurrentLinkedList<V> get(K key) {
		return (ConcurrentLinkedList<V>) super.get(key);
	}

	@Override
	public CopyOnWriteArrayList<V> remove(K key) {
		return (CopyOnWriteArrayList<V>) super.remove(key);
	}

	@Override
	protected Collection<V> createCollection(int capacity) {
		return new CopyOnWriteArrayList<>();
	}

}
