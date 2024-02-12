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
	ComponentHandler createHandler(GUI gui, int slot, int length, int depth, int offsetX, int offsetY);
	
	/**
	 * Creates an ComponentHandler for the GUI
	 * @param gui
	 * @param slot
	 * @param length
	 * @param depth
	 * @return a new ComponentHandler
	 */
	ComponentHandler createHandler(GUI gui, int slot, int length, int depth);

	int getLengthX();
	
	int getLengthY();
	
	List<ComponentHandler> getHandlers();
	
	void remove(ComponentHandler handler);

	E get(int x, int y);
	
	/**
	 * Sets an Entry at x and y and updates all ComponentHandlers
	 * @param x
	 * @param y
	 * @param entry
	 */
	void set(int x, int y, E entry);
	
	/**
	 * Sets an Entry at x and y and updates all ComponentHandlers except the given one
	 * @param x
	 * @param y
	 * @param entry
	 * @param source
	 */
	void set(int x, int y, E entry, ComponentHandler source);
	
	/**
	 * Sets an Entry at x and y
	 * @param x
	 * @param y
	 * @param entry
	 * @param update all ComponentHandlers if true
	 */
	void set(int x, int y, E entry, boolean update);

	default boolean add(E entry) {
		return add(entry, true);
	}
	
	/**
	 * Appends an Entry to the Component
	 * @param entry
	 * @param update
	 * @return false if the Entry could not be stored
	 */
	boolean add(E entry, boolean update);

	/**
	 * Removes the first Entry that is equal and updates
	 * @param entry
	 * @return true if an Entry could be removed
	 */
	default boolean remove(E entry) {
		return remove(entry, true);
	}
	
	/**
	 * Removes the first Entry that is equal
	 * @param entry
	 * @param update
	 * @return true if an Entry could be removed
	 */
	boolean remove(E entry, boolean update);
	
	boolean contains(E entry);
	
	/**
	 * 
	 * @return a the array of entries[y > x]
	 */
	E[] getEntries();
	
	default void clear() {
		clear(true);
	}
	
	void clear(boolean update);
	
	ComponentIterator<E> iterator();

	int getSize();

}
