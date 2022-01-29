package de.atlasmc.util.map;

import java.util.List;

public interface ListMultimap<K, V> extends Multimap<K, V> {

	public List<V> get(K key);
	
	public List<V> remove(K key);
	
}
