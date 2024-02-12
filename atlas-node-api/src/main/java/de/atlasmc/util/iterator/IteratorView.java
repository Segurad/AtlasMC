package de.atlasmc.util.iterator;

import java.util.Iterator;

/**
 * {@link Iterator} implementation that guarantees that no modification are possible through it
 */
public final class IteratorView<E> implements Iterator<E> {

	private final Iterator<E> source;
	
	public IteratorView(Iterator<E> source) {
		this.source = source;
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
