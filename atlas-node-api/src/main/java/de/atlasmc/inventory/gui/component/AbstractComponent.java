package de.atlasmc.inventory.gui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.inventory.gui.GUI;

public abstract class AbstractComponent<E> implements Component<E> {

	protected final List<ComponentHandler> handlers;
	protected final Object[] entries; // ordered y > x
	protected final int x;
	protected final int y;
	
	protected AbstractComponent(int x, int y) {
		handlers = new ArrayList<>();
		this.x = x;
		this.y = y;
		entries = new Object[y*x];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E get(int x, int y) {
		return (E) entries[y*this.x+x];
	}
	
	@Override
	public void set(int x, int y, E entry) {
		set(x, y, entry, true);
	}
	
	@Override
	public void set(int x, int y, E entry, boolean update) {
		entries[y*this.x+x] = entry;
		if (update && !handlers.isEmpty()) {
			for (ComponentHandler h : handlers) {
				h.internalUpdate(x, y);
			}
		}
	}
	
	@Override
	public void set(int x, int y, E entry, ComponentHandler source) {
		if (source == null)
			throw new IllegalArgumentException("Source handler can not be null!");
		if (source.getComponent() != this)
			throw new IllegalArgumentException("Source handler does not belong to this Component!");
		entries[y*this.x+x] = entry;
		if (handlers.size() > 1) {
			for (ComponentHandler h : handlers) {
				if (h != source) 
					h.internalUpdate(x, y);
			}
		}
	}
	
	@Override
	public boolean add(E entry, boolean update) {
		if (entry == null)
			throw new IllegalArgumentException("Entry can not be null!");
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] != null) 
				continue;
			entries[i] = entry;
			if (!handlers.isEmpty() && update) {
				for (ComponentHandler h : handlers) {
					h.internalUpdate(x, y);
				};
			}
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public boolean remove(E entry, boolean update) {
		if (entry == null)
			return false;
		for (int i = 0; i < entries.length; i++) {
			final E etry = (E) entries[i];
			if (entries[i] == null) 
				continue;
			if (!etry.equals(entry)) 
				continue;
			entries[i] = null;
			if (!handlers.isEmpty() && update) {
				for (ComponentHandler h : handlers) {
					h.internalUpdate(x, y);
				};
			}
			return true;
		}
		return false;
	}
	
	@Override
	public int getLengthX() {
		return x;
	}
	
	@Override
	public int getLengthY() {
		return y;
	}

	@Override
	public ComponentHandler createHandler(GUI gui, int slot, int length, int depth) {
		return createHandler(gui, slot, length, depth, 0, 0);
	}
	
	@Override
	public abstract ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int offsetX, int offsetY);
	
	@Override
	public List<ComponentHandler> getHandlers() {
		return handlers;
	}
	
	@Override
	public void remove(ComponentHandler handler) {
		handlers.remove(handler);
	}

	@Override
	public boolean contains(E entry) {
		for (Object o : entries) {
			if (o.equals(entry)) 
				return true;
		}
		return false;
	}
	
	@Override
	public void clear(boolean update) {
		Arrays.fill(entries, null);
		if (update) {
			for (ComponentHandler handler : handlers) {
				handler.updateDisplay();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[] getEntries() {
		return (E[]) entries;
	}
	
	@Override
	public ComponentIterator<E> iterator() {
		return new ComponentIterator<>(this);
	}
	
	@Override
	public int getSize() {
		return getLengthX() * getLengthY();
	}
}
