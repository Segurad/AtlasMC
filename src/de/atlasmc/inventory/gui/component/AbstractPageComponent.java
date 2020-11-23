package de.atlasmc.inventory.gui.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.inventory.gui.GUI;

public abstract class AbstractPageComponent<E> implements PageComponent<E> {

	protected final List<ComponentHandler> handlers;
	protected final List<E[][]> entries;
	protected final Class<E> clazz;
	protected final int x, y, max;
	
	protected AbstractPageComponent(Class<E> clazz, int x, int y) {
		this(clazz, x, y, 0, 1);
	}
	
	protected AbstractPageComponent(Class<E> clazz, int x, int y, int maxpages) {
		this(clazz, x, y, maxpages, 1);
	}
	
	/**
	 * 
	 * @param clazz
	 * @param x
	 * @param y
	 * @param maxpages 0 for no limit
	 * @param pages amount of pages to create
	 */
	protected AbstractPageComponent(Class<E> clazz, int x, int y, int maxpages, int pages) {
		this.clazz = clazz;
		this.x = x;
		this.y = y;
		this.max = maxpages;
		handlers = new ArrayList<ComponentHandler>();
		entries = new ArrayList<>();
		if (pages > 0) {
			for (int i = 0; i < pages; i++) {
				if (entries.size() >= maxpages && maxpages > 0) break;
				@SuppressWarnings("unchecked")
				E[][] entries = (E[][]) Array.newInstance(clazz, y, x);
				this.entries.add(entries);
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
		E[][] entries = this.entries.get(page);
		return entries[ry][x];
	}

	@Override
	public void set(int x, int y, E entry) {
		set(x, y, entry, true);
	}

	@Override
	public void set(int x, int y, E entry, ComponentHandler source) {
		final int page = y/this.y;
		final int ry = y%this.y;
		E[][] entries = this.entries.get(page);
		entries[ry][x] = entry;
		handlers.forEach(h -> { if (h != source) h.internalUpdate(x, y); } );
	}

	@Override
	public void set(int x, int y, E entry, boolean update) {
		final int page = y/this.y;
		final int ry = y%this.y;
		E[][] entries = this.entries.get(page);
		entries[ry][x] = entry;
		if (update) handlers.forEach(h -> h.internalUpdate(x, y));
	}

	@Override
	public boolean add(E entry) {
		return add(entry, true);
	}

	@Override
	public boolean add(E entry, boolean update) {
		int page = 0;
		for (E[][] entries : this.entries) {
			for (int y = 0; y < entries.length; y++) {
				for (int x = 0; x < entries[y].length; x++) {
					if (entries[y][x] != null) continue;
					entries[y][x] = entry;
					if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
						h.internalUpdate(x, page*this.y + y);
					};
					return true;
				}
			}
			page++;
		}
		return false;
	}
	
	@Override
	public boolean remove(E entry) {
		return remove(entry, true);
	}
	
	@Override 
	public boolean remove(E entry, boolean update) {
		for (final E[][] entries : this.entries) {
			for (int y = 0; y < entries.length; y++) {
				for (int x = 0; x < entries[y].length; x++) {
					final E etry = entries[y][x];
					if (entries[y][x] == null) continue;
					if (!etry.equals(entry)) continue;
					entries[y][x] = null;
					if (!handlers.isEmpty() && update) for (ComponentHandler h : handlers) {
						h.internalUpdate(x, y);
					};
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean contains(E entry) {
		for (E[][] entries : this.entries) {
			for (int y = 0; y < entries.length; y++) {
				for (int x = 0; x < entries[y].length; x++) {
					if (entries[y][x].equals(entry)) return true;
				}
			}
		}
		return false;
	}

	@Override
	public Class<E> getType() {
		return clazz;
	}

	@Override
	public void clear() {
		clear(true);
	}
	
	@Override
	public void clear(boolean update) {
		for (final E[][] entries : this.entries) {
			for (E[] e : entries) {
				Arrays.fill(e, null);
			}
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
	
	@Override
	public int addPage() {
		if (entries.size() >= max && max > 0) return -1;
		@SuppressWarnings("unchecked")
		E[][] entries = (E[][]) Array.newInstance(clazz, y, x);
		this.entries.add(entries);
		return this.entries.size()-1;
	}
	
	@Override
	public void addPage(int index) {
		if (entries.size() >= max) return;
		@SuppressWarnings("unchecked")
		E[][] entries = (E[][]) Array.newInstance(clazz, y, x);
		this.entries.add(index, entries);
	}
	
	@Override
	public void removePage(int index) {
		this.entries.remove(index);
	}
	
	@Override
	public E[][] getEntries() {
		return getEntries(0);
	}
	
	@Override
	public E[][] getEntries(int index) {
		return entries.get(index);
	}
	
	@Override
	public List<E[][]> getEntrieList() {
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
