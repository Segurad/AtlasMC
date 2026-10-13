package de.atlasmc.util.dataset;

import java.util.Iterator;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.annotation.NotNull;
import it.unimi.dsi.fastutil.objects.ObjectIterators;

final class EmptyDataSet<T extends Namespaced> extends AbstractDataSet<T> {

	@NotNull
	public static final EmptyDataSet<?> INSTANCE = new EmptyDataSet<>();
	
	private EmptyDataSet() {}

	@Override
	public boolean contains(Object value) {
		return false;
	}
	
	@Override
	public int size() {
		return 0;
	}
	
	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return super.equals(obj);
	}

	@Override
	public Iterator<T> iterator() {
		return ObjectIterators.emptyIterator();
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
