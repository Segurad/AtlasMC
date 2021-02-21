package de.atlasmc.inventory.gui.component;

import de.atlasmc.inventory.gui.Button;
import de.atlasmc.util.Pair;

public interface ComponentHandler {

	/**
	 * Only use by Component
	 * Updates the Component view  if need  
	 * @param x 
	 * @param y
	 * @param value
	 */
	public void internalUpdate(int x, int y);
	public Component<?> getComponent();
	
	/**
	 * Updates the Component view
	 */
	public void updateDisplay();
	
	/**
	 * Updates the Component view
	 * @param x the new upper left x value of the component view
	 * @param y the new upper left y value of the component view
	 */
	public void updateDisplay(int x, int y);
	public int getDisplayX();
	public int getDisplayY();
	
	/**
	 * 
	 * @param slot
	 * @return a Pair with the x,y int
	 */
	public Pair<Integer, Integer> getCoordsBySlot(int slot);
	/**
	 * 
	 * @param x
	 * @param y
	 * @return the slot or -1
	 */
	public int getSlotByCoords(int x, int y);
	public Object getSlotEntry(int slot);
	public void setSlotEntry(int slot, Object entry);
	public void setSlotEntry(int slot, Object entry, boolean update);
	public void setButtons(Button button);
	public void setClickable(boolean value);
	public int getLength();
	public int getDepth();


}
