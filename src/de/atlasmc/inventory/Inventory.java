package de.atlasmc.inventory;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.gui.GUI;

/**
 * Represents a Inventory
 */
public interface Inventory extends Iterable<ItemStack> {
	
	/**
	 * Clears the contents of this Inventory.<br>
	 * {@link #updateSlots()} is required to persist changes to the player
	 */
	void clear();

	int getSize();

	/**
	 * Sets a copy of the given item in the given slot
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 * @param whether or not the item add animation should be played
	 */
	void setItem(int slot, ItemStack item, boolean animation);
	
	/**
	 * Sets a copy of the given item in the given slot
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 */
	void setItem(int slot, ItemStack item);
	
	/**
	 * Sets the given item in the given slot <b>NOT A COPY</b>
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 * @param whether or not the item add animation should be played
	 */
	void setItemUnsafe(int slot, ItemStack item, boolean animation);
	
	/**
	 * Sets the given item in the given slot <b>NOT A COPY</b>
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 */
	void setItemUnsafe(int slot, ItemStack item);
	
	/**
	 * Adds the given item to this inventory and returns the amount remaining of the stack.
	 * <li> fills stacks of the same type
	 * <li> fills empty slots
	 * @param item
	 * @return remaining
	 */
	int addItem(ItemStack item);
	
	/**
	 * Removes all stacks in the inventory that match the given stack
	 * @param item
	 */
	default void remove(ItemStack item) {
		removeSimilar(item, false, false);
	}
	
	/**
	 * Removes all stacks in the inventory that are similar to the given stack
	 * @param item
	 * @param ignoreAmount
	 * @param ignoreDamage
	 */
	void removeSimilar(ItemStack item, boolean ignoreAmount, boolean ignoreDamage);
	
	/**
	 * Removes all stacks in the inventory that match the given material
	 * @param material
	 */
	void remove(Material material);
	
	/**
	 * Adds the given items to this inventory and returns a list with items that could not be added.
	 * <li> fills stacks of the same type
	 * <li> fills empty slots
	 * @param items
	 * @return list
	 */
	List<ItemStack> addItem(ItemStack... items);

	/**
	 * Returns a copy of the item in the given slot or null
	 * @param slot
	 * @return item or null
	 */
	ItemStack getItem(int slot);
	
	/**
	 * Returns the item in the given slot or null <b>NOT A COPY</b>
	 * @param slot
	 * @return item or null
	 */
	ItemStack getItemUnsafe(int slot);
	
	/**
	 * Returns a copy of the contents of this Inventory
	 * @return contents
	 */
	ItemStack[] getContents();
	
	/**
	 * Returns a new Array with the items in this Inventory <b>NO ITEM COPIES</b>
	 * @return contents
	 */
	ItemStack[] getContentsUnsafe();
	
	void setContents(ItemStack[] contents);
	
	void setContentsUnsafe(ItemStack[] contents);
	
	boolean contains(Material material);
	
	int count(Material material);
	
	void removeItems(Material material, int count);

	List<Player> getViewers();
	
	boolean hasViewers();

	Chat getTitle();

	SlotType getSlotType(int slot);

	InventoryType getType();

	InventoryHolder getHolder();
	
	void setHolder(InventoryHolder holder);
	
	/**
	 * Updates a certain slot of this inventory for all viewers<br>
	 * @param slot
	 * @param animation
	 */
	void updateSlot(int slot, boolean animation);
	
	/**
	 * Updates a certain slot of this inventory for a player
	 * @param player
	 * @param slot
	 * @param animation
	 */
	void updateSlot(Player player, int slot, boolean animation);
	
	/**
	 * Updates all slots of this inventory for all viewers
	 */
	void updateSlots();

	/**
	 * Updates all slots of this inventory for a player
	 * @param player
	 */
	void updateSlots(Player player);
	
	void updateProperties();
	
	void updateProperties(Player player);

	/**
	 * Sets the Title of this Inventory. Players need to reopen it to receive the update.
	 * @param title for this Inventory
	 */
	void setTitle(Chat title);

	/**
	 * Returns the number of Slots set in this Inventory
	 * @return slots set
	 */
	int countItems();
	
	/**
	 * Returns an iterator over the items in this inventory <b>NO ITEM COPIES</b> 
	 * @return iterator
	 */
	Iterator<ItemStack> iteratorUnsafe();
	
	GUI getGUI();
	
	void setGUI(GUI gui);
	
}
