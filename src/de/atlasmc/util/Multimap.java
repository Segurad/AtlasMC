package de.atlasmc.util;

import java.util.List;
import java.util.Set;

public interface Multimap<K, V> {

	public Set<K> keySet();

	public List<V> getValues(K key);

	public boolean isEmpty();

	public boolean remove(K key);

}
