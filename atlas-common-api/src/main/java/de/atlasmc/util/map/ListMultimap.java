package de.atlasmc.util.map;

import java.util.List;

public interface ListMultimap<K, V> extends Multimap<K, V> {

	@Override
	List<V> get(K key);
	
	@Override
	List<V> remove(K key);
	
}
