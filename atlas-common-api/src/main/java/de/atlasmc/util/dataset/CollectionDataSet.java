package de.atlasmc.util.dataset;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import de.atlasmc.NamespacedKey.Namespaced;

public final class CollectionDataSet<T extends Namespaced> extends AbstractDataSet<T> {

	private final Set<T> values;

	@SafeVarargs
	public CollectionDataSet(T... values) {
		this.values = Set.of(values);
	}
	
	public CollectionDataSet(Collection<T> values) {
		this.values = Set.copyOf(values);
	}
	
	@Override
	public boolean contains(Object value) {
		return values.contains(value);
	}
	
	@Override
	public int size() {
		return values.size();
	}

	@Override
	public boolean isEmpty() {
		return values.isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return super.equals(obj);
		CollectionDataSet<?> other = (CollectionDataSet<?>) obj;
		return Objects.equals(values, other.values);
	}

	@Override
	public Iterator<T> iterator() {
		return values.iterator();
	}
	
}
