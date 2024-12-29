package de.atlasmc.util.dataset;

import java.util.Collection;
import java.util.List;

import de.atlasmc.NamespacedKey.Namespaced;

public class SingleValueDataSet<T extends Namespaced> implements DataSet<T> {

	private final T value;
	
	public SingleValueDataSet(T value) {
		if (value == null)
			throw new IllegalArgumentException("Value can not be null!");
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public Collection<T> values() {
		return List.of(value);
	}
	
	@Override
	public boolean contains(T value) {
		return this.value.equals(value);
	}
	
	@Override
	public int size() {
		return 1;
	}
	
}
