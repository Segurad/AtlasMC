package de.atlasmc.util.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class UnboxingIterator<E, U> implements Iterator<E> {
	
	private final Iterator<U> it;
	private final Function<U, E> unbox;
	private final boolean canRemove;
	
	private E next;
	
	public UnboxingIterator(Iterator<U> it, Function<U, E> unbox, boolean canRemove) {
		if (it == null)
			throw new IllegalArgumentException("Iterator can not be null!");
		if (unbox == null)
			throw new IllegalArgumentException("Unbox function can not be null!");
		this.it = it;
		this.unbox = unbox;
		this.canRemove = canRemove;
	}

	@Override
	public boolean hasNext() {
		if (next != null)
			return true;
		while (it.hasNext()) {
			U next = it.next();
			E value = this.next = unbox.apply(next);
			if (value != null)
				return true;
		}
		return false;
	}

	@Override
	public E next() {
		if (!hasNext())
			throw new NoSuchElementException();
		E next = this.next;
		this.next = null;
		return next;
	}
	
	@Override
	public void remove() {
		if (canRemove)
			it.remove();
		else
			Iterator.super.remove();
	}

}
