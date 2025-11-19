package de.atlasmc.util.iterator;

import java.util.Iterator;

public class SingleValueIterator<E> implements Iterator<E> {

	private E value;
	
	public SingleValueIterator(E value) {
		this.value = value;
	}
	
	@Override
	public boolean hasNext() {
		return value != null;
	}

	@Override
	public E next() {
		E value = this.value;
		this.value = null;
		return value;
	}

}
