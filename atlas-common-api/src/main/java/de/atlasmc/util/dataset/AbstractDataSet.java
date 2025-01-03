package de.atlasmc.util.dataset;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;

public abstract class AbstractDataSet<T extends Namespaced> implements DataSet<T> {

	@Override
	public boolean contains(T value) {
		return values().contains(value);
	}

	@Override
	public int size() {
		return values().size();
	}

	@Override
	public boolean isEmpty() {
		return values().isEmpty();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DataSet<?> dataSet))
			return false;
		if (size() != dataSet.size())
			return false;
		Collection<T> values = values();
		Collection<?> otherValues = dataSet.values();
		return values.containsAll(otherValues);
	}
	
	@Override
	public int hashCode() {
		return values().hashCode();
	}

}
