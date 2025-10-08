package de.atlasmc.util.iterator;

import java.util.Iterator;

public class ArrayIterator<E> implements Iterator<E> {

	private final E[] array;
	private int index = -1;
	private final int end;
	private final boolean canRemove;
	
	public ArrayIterator(E[] array, boolean canModify) {
		this(array, canModify, 0, array.length);
	}
	
	public ArrayIterator(E[] array, boolean canRemove, int from, int to) {
		this.array = array;
		this.canRemove = canRemove;
		this.index = from;
		this.end = to;
	}
	
	@Override
	public boolean hasNext() {
		return index < end;
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new IndexOutOfBoundsException();
		index++;
		return array[index];

	}

	@Override
	public void remove() {
		if (canRemove)
			array[index] = null;
	}
	
}
