package de.atlasmc.inventory.gui;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.gui.GUIListener.GUIClickListener;
import de.atlasmc.inventory.gui.GUIListener.GUICloseListener;
import de.atlasmc.inventory.gui.GUIListener.GUIOpenListener;
import de.atlasmc.inventory.gui.button.Button;
import de.atlasmc.inventory.gui.component.Component;
import de.atlasmc.inventory.gui.component.ComponentHandler;
import de.atlasmc.util.annotation.InternalAPI;

public interface GUI {

	/**
	 * Returns the button set to the given slot or null if no button present
	 * @param slot
	 * @return button or null
	 */
	Button getButton(int slot);
	
	/**
	 * Returns whether or not a button is at the given slot
	 * @param slot
	 * @return true if button is present
	 */
	boolean isButtonAt(int slot);
	
	void setButton(int slot, Button button);
	
	void setButton(int slot, Button button, boolean icon);
	
	void setButtons(int start, int end, Button button, boolean icon);

	/**
	 * Removes all Buttons from this GUI
	 */
	void clearButtons();
	
	void setClickable(boolean value, int... slot);
	
	boolean isClickable(int slot);
	
	/**
	 * Sets the clickable status for all slots
	 * @param value
	 */
	void setClickable(boolean value);
	
	ComponentHandler addComponent(Component<?> component, int slot, int length, int depth);
	
	void addComponentHandler(ComponentHandler handler);
	
	List<ComponentHandler> getComponents();
	
	void removeComponentHandler(ComponentHandler handler);

	/**
	 * Called by the node when a Player closes the bound Inventory
	 * @param player that closes
	 */
	@InternalAPI
	void notifyClosedBy(Player player);
	
	/**
	 * Called by the node when a Player opens the bound Inventory
	 * @param player that opens
	 */
	@InternalAPI
	void notifyOpenedBy(Player player);
	
	/**
	 * Called by the Inventory if this GUI is removed as GUI
	 */
	@InternalAPI
	void notifyRemoved();
	
	/**
	 * Called by the node when the bound Inventory is clicked
	 * @param event the click event
	 */
	@InternalAPI
	void notifyClick(InventoryClickEvent event);
	
	/**
	 * Adds the given GUIListener to this GUI
	 * @param listener to add
	 */
	void addGUIListener(GUIListener listener);
	
	default void addClickListener(GUIClickListener listener) {
		addGUIListener(listener);
	}
	
	default void addOpenListener(GUIOpenListener listener) {
		addGUIListener(listener);
	}
	
	default void addCloseListener(GUICloseListener listener) {
		addGUIListener(listener);
	}
	
	/**
	 * Returns all listeners of 
	 * @return
	 */
	List<GUIListener> getGUIListeners();
	
	/**
	 * Removes the given GUIListener from this GUI
	 * @param listener to remove
	 */
	void removeGUIListener(GUIListener listener);

	/**
	 * Returns the bound Inventory of this GUI
	 * @return inventory
	 */
	Inventory getInventory();
	
}
