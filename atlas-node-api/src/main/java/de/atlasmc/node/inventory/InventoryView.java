package de.atlasmc.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.inventory.InventoryType.SlotType;

public interface InventoryView {
	
	void close();
	
	/**
	 * Return the type of the top Inventory
	 * @return
	 */
	InventoryType getType();
	
	/**
	 * 
	 * @return the owner of this view
	 */
	Player getPlayer();
	
	Inventory getTopInventory();
	
	Inventory getBottomInventory();
	
	void setCursor(ItemStack item);
	
	void setCursorUnsafe(ItemStack item);
	
	ItemStack getCursor();
	
	ItemStack getCursorUnsafe();

	ItemStack getItem(int rawSlot);
	
	ItemStack getItemUnsafe(int rawSlot);
	
	void setItem(int rawSlot, ItemStack item);
	
	void setItemUnsafe(int rawSlot, ItemStack item);
	
	int countSlots();
	
	int convertSlot(int rawSlot);
	
	/**
	 * Converts from the normal slot to the raw slot
	 * @param inv
	 * @param slot
	 * @return
	 */
	int convertSlot(Inventory inv, int slot);
	
	SlotType getSlotType(int rawSlot);
	
	Inventory getInventory(int rawSlot);
	
	Chat getTitle();
	
	/**
	 * Returns the view id of the current view. 
	 * Will be incremented each time a new inventory is opened. 
	 * Is always 0 for the default inventory.
	 * @return view id
	 */
	int getViewID();
	
}
