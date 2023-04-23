package de.atlasmc.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.InventoryType.SlotType;

public interface InventoryView {
	
	public void close();
	
	/**
	 * Return the type of the top Inventory
	 * @return
	 */
	public InventoryType getType();
	
	/**
	 * 
	 * @return the owner of this view
	 */
	public Player getPlayer();
	
	public Inventory getTopInventory();
	
	public Inventory getBottomInventory();
	
	public void setCursor(ItemStack item);
	
	public void setCursorUnsafe(ItemStack item);
	
	public ItemStack getCursor();
	
	public ItemStack getCursorUnsafe();

	public ItemStack getItem(int rawSlot);
	
	public ItemStack getItemUnsafe(int rawSlot);
	
	public void setItem(int rawSlot, ItemStack item);
	
	public void setItemUnsafe(int rawSlot, ItemStack item);
	
	public int countSlots();
	
	public int convertSlot(int rawSlot);
	
	/**
	 * Converts from the normal slot to the raw slot
	 * @param inv
	 * @param slot
	 * @return
	 */
	public int convertSlot(Inventory inv, int slot);
	
	public SlotType getSlotType(int rawSlot);
	
	public Inventory getInventory(int rawSlot);
	
	public Chat getTitle();
	
	/**
	 * Returns the view id of the current view. 
	 * Will be incremented each time a new inventory is opened. 
	 * Is always 0 for the default inventory.
	 * @return view id
	 */
	public int getViewID();
}
