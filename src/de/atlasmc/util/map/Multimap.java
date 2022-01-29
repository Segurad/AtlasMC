package de.atlasmc.util.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Multimap<K, V> {
	
	public static <K, V> Multimap<K, V> of() {
		return ImmutableMultimap.getEmpty();
	}

	public Set<K> keySet();

	public Collection<V> get(K key);

	public boolean isEmpty();

	public Collection<V> remove(K key);

	public boolean put(K key, V value);
	
	public boolean putAll(K key, Iterable<V> values);
	
	public boolean putAll(Multimap<K, V> map);
	
	public Map<K, Collection<V>> asMap();
	
	public void clear();

	public boolean containsValue(Object value);
	
	public boolean containsKey(Object key);

	public int size();

}
