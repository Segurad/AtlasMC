package de.atlasmc.inventory.gui.component;

import java.util.ListIterator;

public class ComponentIterator<E> implements ListIterator<E> {

	private final Component<E> comp;
	private int x;
	private int y;
	private boolean direction;
	
	public ComponentIterator(Component<E> comp) {
		this.comp = comp;
	}
	
	@Override
	public void add(E e) {
		throw new UnsupportedOperationException("Please use Component<?>.add(? value)");
	}

	@Override
	public boolean hasNext() {
		return x*y < comp.getSize();
	}

	@Override
	public boolean hasPrevious() {
		return x*y > 0;
	}

	@Override
	public E next() {
		direction = true;
		if (x >= comp.getLengthX()) 
			x = 0;
		return comp.get(x++, y++);
	}

	@Override
	public int nextIndex() {
		return x*y;
	}

	@Override
	public E previous() {
		direction = false;
		if (x == 0) {
			x = comp.getLengthX()-1;
			y--;
		}
		return comp.get(--x, --y);
	}

	@Override
	public int previousIndex() {
		return x*y-1;
	}

	@Override
	public void remove() {
		int x = direction ? this.x-1 : this.x, y = this.y;
		if (x < 0) {
			x = comp.getLengthX()-1;
			y--;
		}
		comp.set(x, y, null);
	}

	@Override
	public void set(E e) {
		int x = direction ? this.x-1 : this.x, y = this.y;
		if (x < 0) {
			x = comp.getLengthX()-1;
			y--;
		}
		comp.set(x, y, e);
	}

}
