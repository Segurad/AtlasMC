package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.gui.button.Button;
import de.atlasmc.util.Pair;

public interface ComponentHandler {

	/**
	 * Only use by Component
	 * Updates the Component view  if need  
	 * @param x 
	 * @param y
	 */
	void internalUpdate(int x, int y);
	
	Component<?> getComponent();
	
	/**
	 * Updates the Component view
	 */
	void updateDisplay();
	
	/**
	 * Updates the Component view
	 * @param x the new upper left x value of the component view
	 * @param y the new upper left y value of the component view
	 */
	void updateDisplay(int x, int y);
	
	int getDisplayX();
	
	int getDisplayY();
	
	/**
	 * 
	 * @param slot
	 * @return a Pair with the x,y int
	 */
	Pair<Integer, Integer> getCoordsBySlot(int slot);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return the slot or -1
	 */
	int getSlotByCoords(int x, int y);
	
	Object getSlotEntry(int slot);
	
	void setSlotEntry(int slot, Object entry);
	
	void setSlotEntry(int slot, Object entry, boolean update);
	
	void setButtons(Button button);
	
	void setClickable(boolean value);
	
	int getLength();
	
	int getDepth();

}
