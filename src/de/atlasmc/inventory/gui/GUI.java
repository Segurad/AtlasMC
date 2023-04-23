package de.atlasmc.inventory.gui;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryClickEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.gui.button.Button;
import de.atlasmc.inventory.gui.component.Component;
import de.atlasmc.inventory.gui.component.ComponentHandler;

public interface GUI {

	Button getButton(int slot);
	
	boolean isButtonAt(int slot);
	
	void setButton(int slot, Button button);
	
	void setButton(int slot, Button button, boolean icon);
	
	void setButtons(int start, int end, Button button, boolean icon);

	void setClickable(boolean value, int... slot);
	
	boolean isClickable(int slot);
	
	/**
	 * Sets the clickable status for all slots
	 * @param value
	 */
	void setClickable(boolean value);

	void clearButtons();
	
	ComponentHandler addComponent(Component<?> component, int slot, int length, int depth);
	
	void addComponentHandler(ComponentHandler handler);
	
	List<ComponentHandler> getComponents();
	
	void removeComponentHandler(ComponentHandler handler);

	void notifyClosedBy(Player player);
	
	void notifyOpenedBy(Player player);
	
	/**
	 * Called by the inventory if this gui is removed as gui
	 */
	void notifyRemoved();
	
	void notifyClick(InventoryClickEvent event);
	
	void addGUIListener(GUIListener listener);
	
	List<GUIListener> getGUIListeners();
	
	void removeGUIListener(GUIListener listener);

	Inventory getInventory();
	
}
