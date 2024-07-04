package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;

import de.atlasmc.util.ConcurrentLinkedList;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public class ConcurrentLinkedListMultimap<K, V> extends AbstractConcurrentMultimap<K, V> {
	
	public ConcurrentLinkedListMultimap() {
		super();
	}
	
	public ConcurrentLinkedListMultimap(Map<K, Collection<V>> map) {
		super(map, DEFAULT_COLLECTION_CAPACITY);
	}
	
	public ConcurrentLinkedListMultimap(int mapCapacity) {
		super(mapCapacity);
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
	protected Collection<V> createCollection(int capacity) {
		return new ConcurrentLinkedList<>();
	}

}
