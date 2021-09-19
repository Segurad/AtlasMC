package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;

/**
 * Represents a Inventory
 */
public interface Inventory extends Iterable<ItemStack> {

	public int getSize();

	public void setItem(int slot, ItemStack item, boolean animation);
	
	public void setItem(int slot, ItemStack item);

	public ItemStack getItem(int slot);
	
	public ItemStack[] getContents();
	
	public void setContents(ItemStack[] contents);
	
	public boolean contains(Material material);
	
	public int count(Material material);
	
	public void removeItems(Material material, int count);

	public List<Player> getViewers();
	
	public boolean hasViewers();

	public ChatComponent getTitle();

	public SlotType getSlotType(int slot);

	public InventoryType getType();

	public InventoryHolder getHolder();
	
	public void setHolder(InventoryHolder holder);
	
	/**
	 * Updates a certain slot of this inventory for all viewers<br>
	 * @param slot
	 * @param animation
	 */
	public void updateSlot(int slot, boolean animation);
	
	/**
	 * Updates a certain slot of this inventory for a player<br>
	 * @param player
	 * @param slot
	 * @param animation
	 */
	public void updateSlot(Player player, int slot, boolean animation);
	
	/**
	 * Updates all slots of this inventory for all viewers<br>
	 */
	public void updateSlots();

	/**
	 * Updates all slots of this inventory for a player<br>
	 */
	public void updateSlots(Player player);
	
	public void updateProperties();
	
	public void updateProperties(Player player);

	/**
	 * Sets the Title of this Inventory<br>
	 * Players need to reopen it to receive the update
	 * @param title for this Inventory
	 */
	public void setTitle(ChatComponent title);

	/**
	 * Returns the number of Slots set in this Inventory
	 * @return slots set
	 */
	public int countItems();

}
