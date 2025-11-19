package de.atlasmc.util.dataset;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.iterator.SingleValueIterator;

public class SingleValueDataSet<T extends Namespaced> extends AbstractDataSet<T> {

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
	
	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return super.equals(obj);
		SingleValueDataSet<?> other = (SingleValueDataSet<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public Iterator<T> iterator() {
		return new SingleValueIterator<>(value);
	}
	
}
