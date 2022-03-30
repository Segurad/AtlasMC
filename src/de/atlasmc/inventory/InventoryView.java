package de.atlasmc.inventory;

import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;

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
	
	public ItemStack getCursor();

	public ItemStack getItem(int rawSlot);
	
	public void setItem(int rawSlot, ItemStack item);
	
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
	
	public int getViewID();
}
