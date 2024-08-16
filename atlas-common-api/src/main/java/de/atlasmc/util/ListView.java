package de.atlasmc.util;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import de.atlasmc.util.iterator.ListIteratorView;

/**
 * Implementation of {@link List} that only allows to view the contents
 * @param <E>
 */
public class ListView<E> extends AbstractCollectionView<E, List<E>> implements List<E> {
	
	public ListView(List<E> list) {
		super(list);
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}

	@Override
	public E get(int index) {
		return collection.get(index);
	}

	@Override
	public E set(int index, E element) {
		return null;
	}

	@Override
	public void add(int index, E element) {}

	@Override
	public E remove(int index) {
		return null;
	}

	@Override
	public int indexOf(Object o) {
		return collection.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return collection.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return new ListIteratorView<>(collection.listIterator());
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return new ListIteratorView<>(collection.listIterator(index));
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return collection.subList(fromIndex, toIndex);
	}

}
