package de.atlasmc.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.function.BiConsumer;

/**
 * Set of Viewers of a Holder
 * @param <H> holder of the set
 * @param <V> viewer of this holder
 */
public class ViewerSet<H, V> extends HashSet<V> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final H holder;
	private final BiConsumer<H, V> addFunction;
	private final BiConsumer<H, V> removeFunction;
	
	private boolean hidden;
	
	/**
	 * 
	 * @param holder of this {@link ViewerSet}
	 * @param addFunction function that is called when a viewer is added
	 * @param removeFunction function that is called when a viewer is removed
	 */
	public ViewerSet(H holder, BiConsumer<H, V> addFunction, BiConsumer<H, V> removeFunction) {
		this.holder = holder;
		this.addFunction = addFunction;
		this.removeFunction = removeFunction;
	}
	
	@Override
	public boolean add(V e) {
		if (e == null || !super.add(e))
			return false;
		addFunction.accept(holder, e);
		return true;
	}
	
	/**
	 * Returns whether or not the holder of this {@link ViewerSet} is hidden or not.<br>
	 * When the holder is hidden no viewers should be added automatically.
	 * @return
	 */
	public boolean isHidden() {
		return hidden;
	}
	
	public void setHidden(boolean hidden) {
		setHidden(hidden, false);
	}

	/**
	 * Sets whether or not the holder of this {@link ViewerSet} should be hidden or not.
	 * @param hidden the hidden state of the holder
	 * @param keepViewers whether or not the current viewers should be kept when hidden is true, ignored otherwise
	 */
	public void setHidden(boolean hidden, boolean keepViewers) {
		if (this.hidden == hidden)
			return;
		this.hidden = hidden;
		if (hidden && !keepViewers)
			clear();
	}
	
	@Override
	public void clear() {
		for (V viewer : this) {
			removeFunction.accept(holder, viewer);
		}
		super.clear();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		if (o == null || !super.remove(o))
			return false;
		removeFunction.accept(holder, (V) o);
		return true;
	}
	
	@Override
	public ViewerSet<H, V> clone() {
		throw new UnsupportedOperationException("Clone not supported for ViewerSet!");
	}
	
	@Override
	public Iterator<V> iterator() {
		return new ViewerIterator<>(this, super.iterator());
	}
	
	private class ViewerIterator<E> implements Iterator<E> {
		
		private final ViewerSet<H, E> set;
		private final Iterator<E> iterator;
		private E lastReturned;
		
		public ViewerIterator(ViewerSet<H, E> set, Iterator<E> iterator) {
			this.set = set;
			this.iterator = iterator;
		}
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public E next() {
			lastReturned = iterator.next();
			return lastReturned;
		}
		
		@Override
		public void remove() {
			iterator.remove();
			if (lastReturned != null)
				set.removeFunction.accept(holder, lastReturned);
			lastReturned = null;
		}
		
	}

}
