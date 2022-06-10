package de.atlasmc.util.iterator;

import java.util.ListIterator;

/**
 * {@link ListIterator} implementation that guarantees that no modification are possible through it
 */
public final class ListIteratorView<E> implements ListIterator<E> {

	private final ListIterator<E> source;

	public ListIteratorView(ListIterator<E> source) {
		this.source = source;
	}

	@Override
	public boolean hasPrevious() {
		return source.hasPrevious();
	}

	@Override
	public E previous() {
		return source.previous();
	}

	@Override
	public int nextIndex() {
		return source.nextIndex();
	}

	@Override
	public int previousIndex() {
		return source.previousIndex();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove");
	}

	@Override
	public void set(E e) {
		throw new UnsupportedOperationException("set");
	}

	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("add");
	}

	@Override
	public boolean hasNext() {
		return source.hasNext();
	}

	@Override
	public E next() {
		return source.next();
	}

}
