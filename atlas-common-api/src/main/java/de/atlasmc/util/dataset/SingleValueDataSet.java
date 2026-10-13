package de.atlasmc.util.dataset;

import java.util.Iterator;
import java.util.Objects;

import de.atlasmc.NamespacedKey.Namespaced;
import it.unimi.dsi.fastutil.objects.ObjectIterators;

public final class SingleValueDataSet<T extends Namespaced> extends AbstractDataSet<T> {

	private final T value;
	
	public SingleValueDataSet(T value) {
		this.value = Objects.requireNonNull(value, "value");
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public boolean contains(Object value) {
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
		return value.hashCode();
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
		return ObjectIterators.singleton(value);
	}
	
}
