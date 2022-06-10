package de.atlasmc.util.iterator;

import java.util.Iterator;

public class ArrayIterator<E> implements Iterator<E> {

	private final E[] array;
	private int index;
	private final boolean canRemove;
	
	public ArrayIterator(E[] array, boolean canRemove) {
		this.array = array;
		this.canRemove = canRemove;
	}
	
	@Override
	public boolean hasNext() {
		for (int i = index+1; i < array.length; i++)
			if (array[i] != null)
				return true;
		return false;
	}

	@Override
	public E next() {
		index++;
		for (; index < array.length; index++)
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
