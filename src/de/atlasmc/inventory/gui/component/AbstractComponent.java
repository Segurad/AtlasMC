package de.atlasmc.inventory.gui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.inventory.gui.GUI;

public abstract class AbstractComponent<E> implements Component<E> {

	protected final List<ComponentHandler> handlers;
	protected final Object[][] entries;
	
	protected AbstractComponent(int x, int y) {
		handlers = new ArrayList<ComponentHandler>();
		entries = new Object[y][x];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public E get(int x, int y) {
		if (y >= entries.length || x >= entries[y].length) return null;
		return (E) entries[y][x];
	}
	
	@Override
	public void set(int x, int y, E entry) {
		entries[y][x] = entry;
		handlers.forEach(h -> h.internalUpdate(x, y));
	}
	
	@Override
	public void set(int x, int y, E entry, boolean update) {
		entries[y][x] = entry;
		if (update) handlers.forEach(h -> h.internalUpdate(x, y));
	}
	
	@Override
	public void set(int x, int y, E entry, ComponentHandler source) {
		entries[y][x] = entry;
		handlers.forEach(h -> { if (h != source) h.internalUpdate(x, y); } );
	}
	
	@Override
	public boolean add(E entry, boolean update) {
		for (int y = 0; y < entries.length; y++) {
			for (int x = 0; x < entries[y].length; x++) {
				if (entries[y][x] != null) continue;
				entries[y][x] = entry;
				if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
					h.internalUpdate(x, y);
				};
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override 
	public boolean remove(E entry, boolean update) {
		for (int y = 0; y < entries.length; y++) {
			for (int x = 0; x < entries[y].length; x++) {
				final E etry = (E) entries[y][x];
				if (entries[y][x] == null) continue;
				if (!etry.equals(entry)) continue;
				entries[y][x] = null;
				if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
					h.internalUpdate(x, y);
				};
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int getLengthX() {
		return entries[0].length;
	}
	
	@Override
	public int getLengthY() {
		return entries.length;
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
		for (int y = 0; y < entries.length; y++) {
			for (int x = 0; x < entries[y].length; x++) {
				if (entries[y][x].equals(entry)) return true;
			}
		}
		return false;
	}
	
	@Override
	public void clear(boolean update) {
		for (Object[] e : entries) {
			Arrays.fill(e, null);
		}
		if (update) handlers.forEach(h -> h.updateDisplay());
	}

	@SuppressWarnings("unchecked")
	@Override
	public E[][] getEntries() {
		return (E[][]) entries;
	}
	
	@Override
	public ComponentIterator<E> iterator() {
		return new ComponentIterator<E>(this);
	}
	
	@Override
	public int getSize() {
		return getLengthX() * getLengthY();
	}
}
