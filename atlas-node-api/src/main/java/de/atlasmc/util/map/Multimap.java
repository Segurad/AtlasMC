package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Multimap<K, V> {
	
	public static <K, V> Multimap<K, V> of() {
		return ImmutableMultimap.getEmpty();
	}

	Set<K> keySet();

	/**
	 * Returns the collection for this key or null if not present
	 * @param key
	 * @return collection or null
	 */
	Collection<V> get(K key);

	boolean isEmpty();

	Collection<V> remove(K key);

	boolean put(K key, V value);
	
	boolean putAll(K key, Collection<V> values);
	
	boolean putAll(K key, Iterable<V> values);
	
	boolean putAll(Multimap<K, V> map);
	
	Map<K, Collection<V>> asMap();
	
	void clear();

	boolean containsValue(Object value);
	
	boolean containsKey(Object key);

	int size();

}
