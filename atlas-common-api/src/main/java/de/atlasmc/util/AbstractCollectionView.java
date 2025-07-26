package de.atlasmc.util;

import java.util.Collection;
import java.util.Iterator;

public class AbstractCollectionView<E, C extends Collection<E>> implements Collection<E> {

	protected final C collection;
	
	public AbstractCollectionView(C collection) {
		this.collection = collection;
	}
	
	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return collection.iterator();
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return collection.toArray(a);
	}

	@Override
	public boolean add(E e) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}

	@Override
	public void clear() {
		// not required
	}

}
