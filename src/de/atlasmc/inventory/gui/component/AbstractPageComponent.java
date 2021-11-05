package de.atlasmc.inventory.gui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.inventory.gui.GUI;

public abstract class AbstractPageComponent<E> implements PageComponent<E> {

	protected final List<ComponentHandler> handlers;
	protected final List<E[]> entries;
	protected final int x, y, max;
	
	protected AbstractPageComponent(int x, int y) {
		this(x, y, 0, 1);
	}
	
	protected AbstractPageComponent(int x, int y, int maxpages) {
		this(x, y, maxpages, 1);
	}
	
	/**
	 * 
	 * @param clazz
	 * @param x
	 * @param y
	 * @param maxpages 0 for no limit
	 * @param pages amount of pages to create
	 */
	@SuppressWarnings("unchecked")
	protected AbstractPageComponent(int x, int y, int maxpages, int pages) {
		this.x = x;
		this.y = y;
		this.max = maxpages;
		handlers = new ArrayList<ComponentHandler>();
		entries = new ArrayList<>();
		if (pages > 0) {
			for (int i = 0; i < pages; i++) {
				if (entries.size() >= maxpages && maxpages > 0) break;
				this.entries.add((E[]) new Object[y*x]);
			}
		}
	}
	
	@Override
	public abstract ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int offsetX, int offsetY);

	@Override
	public ComponentHandler createHandler(GUI gui, int slot, int length, int depth) {
		return createHandler(gui, slot, length, depth, 0, 0);
	}

	@Override
	public int getLengthX() {
		return x;
	}

	@Override
	public int getLengthY() {
		return y*entries.size();
	}

	@Override
	public List<ComponentHandler> getHandlers() {
		return handlers;
	}

	@Override
	public void remove(ComponentHandler handler) {
		handlers.remove(handler);
	}

	@Override
	public E get(int x, int y) {
		final int page = y/this.y;
		final int ry = y%this.y;
		E[] entries = this.entries.get(page);
		return entries[ry*this.x+x];
	}

	@Override
	public void set(int x, int y, E entry) {
		set(x, y, entry, true);
	}

	@Override
	public void set(int x, int y, E entry, ComponentHandler source) {
		final int page = y/this.y;
		final int ry = y%this.y;
		Object[] entries = this.entries.get(page);
		entries[ry*this.x+x] = entry;
		handlers.forEach(h -> { if (h != source) h.internalUpdate(x, y); } );
	}

	@Override
	public void set(int x, int y, E entry, boolean update) {
		final int page = y/this.y;
		final int ry = y%this.y;
		Object[] entries = this.entries.get(page);
		entries[ry*this.x+x] = entry;
		if (update) handlers.forEach(h -> h.internalUpdate(x, y));
	}

	@Override
	public boolean add(E entry, boolean update) {
		int page = 0;
		for (Object[] entries : this.entries) {
			for (int i = 0; i < entries.length; i++) {
				if (entries[i] != null) continue;
				entries[i] = entry;
				if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
					h.internalUpdate(x, page*this.y + y);
				};
				return true;
			}
			page++;
		}
		return false;
	}
	
	@Override 
	public boolean remove(E entry, boolean update) {
		for (final Object[] entries : this.entries) {
			for (int i = 0; i < entries.length; i++) {
				final Object etry = entries[i];
				if (entries[i] == null) continue;
				if (!etry.equals(entry)) continue;
				entries[i] = null;
				if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
					h.internalUpdate(x, y);
				};
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(E entry) {
		for (Object[] entries : this.entries) {
			for (int i = 0; i < entries.length; i++) {
				if (entries[i].equals(entry)) return true;
			}
		}
		return false;
	}
	
	@Override
	public void clear(boolean update) {
		for (final Object[] entries : this.entries) {
			Arrays.fill(entries, null);
		}
		if (update) handlers.forEach(h -> h.updateDisplay());
	}
	
	@Override
	public int getPages() {
		return entries.size();
	}
	
	@Override
	public int getMaxPages() {
		return max;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int addPage() {
		if (entries.size() >= max && max > 0) return -1;
		this.entries.add((E[]) new Object[y*x]);
		return this.entries.size()-1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addPage(int index) {
		if (entries.size() >= max) return;
		this.entries.add(index, (E[]) new Object[y*x]);
	}
	
	@Override
	public void removePage(int index) {
		this.entries.remove(index);
	}
	
	@Override
	public E[] getEntries() {
		return getEntries(0);
	}
	
	@Override
	public E[] getEntries(int index) {
		return entries.get(index);
	}
	
	@Override
	public List<E[]> getEntrieList() {
		return entries;
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
