package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;

public interface Inventory extends Iterable<ItemStack> {

	public int getSize();

	public void setItem(int slot, ItemStack item);

	public ItemStack getItem(int slot);

	public List<Player> getViewers();
	
	public void updateSlot(int slot);

	public String getTitle();

	public SlotType getSlotType(int slot);

	public InventoryType getType();

	public InventoryHolder getHolder();

}
