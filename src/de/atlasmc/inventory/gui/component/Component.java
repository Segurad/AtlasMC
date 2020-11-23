package de.atlasmc.inventory.gui.component;

import java.util.List;

import de.atlasmc.inventory.gui.GUI;

public interface Component<E> extends Iterable<E> {

	/**
	 * Creates an ComponentHandler for the GUI
	 * @param gui
	 * @param slot
	 * @param length
	 * @param depth
	 * @param offsetX
	 * @param offsetY
	 * @return a new ComponentHandler
	 */
	public ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int offsetX, int offsetY);
	
	/**
	 * Creates an ComponentHandler for the GUI
	 * @param gui
	 * @param slot
	 * @param length
	 * @param depth
	 * @return a new ComponentHandler
	 */
	public ComponentHandler createHandler(GUI gui, int slot, int length, int depth);

	public int getLengthX();
	public int getLengthY();
	
	public List<ComponentHandler> getHandlers();
	public void remove(ComponentHandler handler);

	public E get(int x, int y);
	/**
	 * Sets an Entry at x and y and updates all ComponentHandlers
	 * @param x
	 * @param y
	 * @param entry
	 */
	public void set(int x, int y, E entry);
	/**
	 * Sets an Entry at x and y and updates all ComponentHandlers except the given one
	 * @param x
	 * @param y
	 * @param entry
	 * @param source
	 */
	public void set(int x, int y, E entry, ComponentHandler source);
	/**
	 * Sets an Entry at x and y
	 * @param x
	 * @param y
	 * @param entry
	 * @param update all ComponentHandlers if true
	 */
	public void set(int x, int y, E entry, boolean update);

	public boolean add(E entry);
	/**
	 * Appends an Entry to the Component
	 * @param entry
	 * @param update
	 * @return false if the Entry could not be stored
	 */
	public boolean add(E entry, boolean update);

	public boolean remove(E entry);
	
	/**
	 * Removes the first Entry that is equal
	 * @param entry
	 * @param update
	 * @return true if an Entry could be removed
	 */
	public boolean remove(E entry, boolean update);
	
	public boolean contains(E entry);
	
	/**
	 * 
	 * @return a the array of entries[y][x]
	 */
	public E[][] getEntries();
	
	public Class<?> getType();
	
	public void clear();
	public void clear(boolean update);
	
	public ComponentIterator<E> iterator();

	public int getSize();

}
