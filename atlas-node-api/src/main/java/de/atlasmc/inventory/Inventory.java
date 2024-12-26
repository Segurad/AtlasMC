package de.atlasmc.inventory;

import java.util.Iterator;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.Chat;
import de.atlasmc.entity.Player;
import de.atlasmc.inventory.InventoryType.SlotType;
import de.atlasmc.inventory.gui.GUI;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.UnsafeAPI;

/**
 * Represents a Inventory
 */
public interface Inventory extends Iterable<ItemStack> {
	
	/**
	 * Returns the current state id
	 * @return state id
	 */
	int getStateID();
	
	/**
	 * Increments the state id and returns it
	 * @return state id
	 */
	int updateStateID();
	
	/**
	 * Clears the contents of this Inventory.<br>
	 * {@link #updateSlots()} is required to persist changes to the player
	 */
	void clear();

	/**
	 * Returns the number of slots
	 * @return slot count
	 */
	int getSize();

	/**
	 * Sets a copy of the given item in the given slot
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 * @param animation whether or not the item add animation should be played
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
	 * @param animation whether or not the item add animation should be played
	 */
	@UnsafeAPI
	void setItemUnsafe(int slot, ItemStack item, boolean animation);
	
	/**
	 * Sets the given item in the given slot <b>NOT A COPY</b>
	 * @param slot the slot to set the item in
	 * @param item the item set in the given slot
	 */
	@UnsafeAPI
	void setItemUnsafe(int slot, ItemStack item);
	
	/**
	 * Adds the given item to this inventory and returns the amount remaining of the stack.
	 * <ul>
	 * <li> fills stacks of the same type</li>
	 * <li> fills empty slots</li>
	 * </ul>
	 * @param item
	 * @return remaining
	 */
	int addItem(ItemStack item);
	
	/**
	 * Removes all stacks in the inventory that match the given stack
	 * @param item
	 */
	default void remove(ItemStack item) {
		removeSimilar(item, false);
	}
	
	/**
	 * Removes all stacks in the inventory that are similar to the given stack
	 * @param item
	 * @param ignoreAmount
	 */
	void removeSimilar(ItemStack item, boolean ignoreAmount);
	
	/**
	 * Removes all stacks in the inventory that match the given material
	 * @param material
	 */
	void remove(Material material);
	
	/**
	 * Adds the given items to this inventory and returns a list with items that could not be added.
	 * <ul>
	 * <li>fills stacks of the same type</li>
	 * <li>fills empty slots</li>
	 * </ul>
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
	@UnsafeAPI
	ItemStack getItemUnsafe(int slot);
	
	/**
	 * Returns a copy of the contents of this Inventory
	 * @return contents
	 */
	ItemStack[] getContents();
	
	/**
	 * Returns a array with the items in this Inventory <b>NO ITEM COPIES</b>
	 * Changes to this array will be present in the inventory
	 * @return contents
	 */
	@UnsafeAPI
	ItemStack[] getContentsUnsafe();
	
	void setContents(ItemStack[] contents);
	
	@UnsafeAPI
	void setContentsUnsafe(ItemStack[] contents);
	
	/**
	 * Returns whether or not this inventory contains a {@link ItemStack} with the given Material
	 * @param material
	 * @return true of present
	 */
	boolean contains(Material material);
	
	/**
	 * Returns the total amount of items with this material
	 * @param material 
	 * @return total amount
	 */
	int count(Material material);
	
	void removeItems(Material material, int count);

	List<Player> getViewers();
	
	/**
	 * Returns whether or not this inventory has viewers
	 * @return true if has viewers
	 */
	boolean hasViewers();

	/**
	 * Returns the displayed title of this inventory or null if none
	 * @return title
	 */
	@Nullable
	Chat getTitle();

	/**
	 * Returns the slots type at the given slot index
	 * @param slot the index of the slot
	 * @return type
	 */
	SlotType getSlotType(int slot);

	/**
	 * Returns the type of this inventory
	 * @return inventory type
	 */
	InventoryType getType();

	/**
	 * Returns the holder e.g. a {@link Player}
	 * @return holder
	 */
	@Nullable
	InventoryHolder getHolder();
	
	/**
	 * Sets a new holder
	 * @param holder
	 */
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
	 * Updates all slots of this inventory for a player.
	 * @param player
	 */
	void updateSlots(Player player);
	
	/**
	 * Updates the properties of this inventory to all {@link #getViewers()}
	 */
	void updateProperties();
	
	/**
	 * Updates the properties of this inventory to the given player.
	 * @param player to update
	 */
	void updateProperties(Player player);

	/**
	 * Sets the title of this inventory. Players need to reopen it to receive the update.
	 * @param title for this inventory
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
	@UnsafeAPI
	Iterator<ItemStack> iteratorUnsafe();
	
	/**
	 * Returns the {@link GUI} currently set or null
	 * @return GUI or null
	 */
	@Nullable
	GUI getGUI();
	
	/**
	 * Sets a GUI or removes the current if set to null
	 * @param gui or null
	 */
	void setGUI(@Nullable GUI gui);

	/**
	 * Sets whether or not this inventory should propagate changes to its {@link #getViewers()}.
	 * Returns the previous auto update state.
	 * If set to false no changes will be updated and the state id will not be incremented.
	 * If preventing updates and making changes {@link #updateStateID()} must be called before this inventory is updated.
	 * @param update true if auto update
	 * @return previous auto update state
	 */
	boolean setAutoUpdate(boolean update);
	
	/**
	 * Returns whether or not this inventory propagates changes to its {@link #getViewers()}
	 * @return auto update state
	 */
	boolean getAutoUpdate();
	
}
