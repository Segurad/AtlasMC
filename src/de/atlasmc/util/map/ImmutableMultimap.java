package de.atlasmc.util.map;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ImmutableMultimap<K, V> extends AbstractMultimap<K, V> implements Multimap<K, V> {
	
	private static final Multimap<?, ?> EMPTY = new ImmutableMultimap<>(Map.of());

	public ImmutableMultimap(Multimap<K, V> map) {
		this(map.asMap());
	}
	
	@SuppressWarnings("unchecked")
	public ImmutableMultimap(Map<K, Collection<V>> map) {
		super((Map<K, Collection<V>>) buildImmutable(map));
	}
	
	@SuppressWarnings("unchecked")
	private static <K, V> Map<V, Collection<V>> buildImmutable(Map<K, Collection<V>> map) {
		if (map.isEmpty())
			return Map.of();
		Entry<K, Collection<V>>[] entries = new Entry[map.size()];
		int i = 0;
		map.forEach((k, v) -> { 
			entries[i] = new SimpleImmutableEntry<>(k, List.copyOf(v)); 
		});
		return (Map<V, Collection<V>>) Map.ofEntries(entries);
	}
	
	@Override
	public Collection<V> remove(K key) {
		return List.of();
	}

	@Override
	public boolean put(K key, V value) {
		return false;
	}

	@Override
	public boolean putAll(K key, Iterable<V> values) {
		return false;
	}
	
	@Override
	public boolean putAll(Multimap<K, V> map) {
		return false;
	}
	
	@Override
	public boolean containsValue(Object value) {
		return false;
	}
	
	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	protected Collection<V> createCollection() {
		return List.of();
	}

	@SuppressWarnings("unchecked")
	public static <K, V> Multimap<K, V> getEmpty() {
		return (Multimap<K, V>) EMPTY;
	}

}
