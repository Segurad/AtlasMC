package de.atlasmc.util.dataset;

import java.util.Collection;
import java.util.Set;

import de.atlasmc.NamespacedKey.Namespaced;

public class CollectionDataSet<T extends Namespaced> implements DataSet<T> {

	private final Set<T> values;

	@SafeVarargs
	public CollectionDataSet(T... values) {
		if (values == null)
			throw new IllegalArgumentException("Values can not be null!");
		this.values = Set.of(values);
	}
	
	public CollectionDataSet(Collection<T> values) {
		if (values == null)
			throw new IllegalArgumentException("Values can not be null!");
		this.values = Set.copyOf(values);
	}
	
	@Override
	public Collection<T> values() {
		return values;
	}
	
	@Override
	public boolean contains(T value) {
		return values.contains(value);
	}
	
	@Override
	public int size() {
		return values.size();
	}
	
}
