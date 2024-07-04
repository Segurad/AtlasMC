package de.atlasmc.util.iterator;

import java.util.Iterator;

public class ArrayIterator<E> implements Iterator<E> {

	private final E[] array;
	private int index;
	private int end;
	private final boolean canRemove;
	
	public ArrayIterator(E[] array, boolean canRemove) {
		this(array, canRemove, 0, array.length);
	}
	
	public ArrayIterator(E[] array, boolean canRemove, int from, int to) {
		this.array = array;
		this.canRemove = canRemove;
		this.index = from;
		this.end = to;
	}
	
	@Override
	public boolean hasNext() {
		for (int i = index+1; i < end; i++)
			if (array[i] != null)
				return true;
		return false;
	}

	@Override
	public E next() {
		index++;
		for (; index < end; index++)
			if (array[index] != null)
				return array[index];
		return null;
	}

	@Override
	public void remove() {
		if (canRemove)
			array[index] = null;
		else
			Iterator.super.remove();
	}
	
}
