package de.atlasmc.node.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.node.entity.Player;
import de.atlasmc.node.inventory.InventoryType.SlotType;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.UnsafeAPI;

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
	@NotNull
	Player getPlayer();
	
	Inventory getTopInventory();
	
	@NotNull
	Inventory getBottomInventory();
	
	/**
	 * Sets the item on the cursor.
	 * @param item
	 */
	@UnsafeAPI
	void setCursor(ItemStack item);
	
	/**
	 * Sets the item on the cursor.
	 * @param item
	 */
	@UnsafeAPI
	void setCursorUnsafe(ItemStack item);
	
	ItemStack getCursor();
	
	@UnsafeAPI
	ItemStack getCursorUnsafe();

	ItemStack getItem(int rawSlot);
	
	@UnsafeAPI
	ItemStack getItemUnsafe(int rawSlot);
	
	void setItem(int rawSlot, ItemStack item);
	
	@UnsafeAPI
	void setItemUnsafe(int rawSlot, ItemStack item);
	
	int countSlots();
	
	int convertSlot(int rawSlot);
	
	boolean isValidSlot(int rawSlot);
	
	/**
	 * Converts from the normal slot to the raw slot
	 * @param inv
	 * @param slot
	 * @return
	 */
	int convertSlot(Inventory inv, int slot);
	
	SlotType getSlotType(int rawSlot);
	
	/**
	 * Returns the inventory matching the raw slot or null if it is outside
	 * @param rawSlot
	 * @return inventory or null
	 * @throws IllegalArgumentException if the slot is invalid
	 */
	@Nullable
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
