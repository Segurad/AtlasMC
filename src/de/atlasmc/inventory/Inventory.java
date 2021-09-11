package de.atlasmc.inventory;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.entity.Player;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.event.inventory.InventoryType.SlotType;

public interface Inventory extends Iterable<ItemStack> {

	public int getSize();

	public void setItem(int slot, ItemStack item);

	public ItemStack getItem(int slot);
	
	public ItemStack[] getContents();
	
	public void setContents(ItemStack[] contents);
	
	public boolean contains(Material material);
	
	public int count(Material material);

	public List<Player> getViewers();

	public String getTitle();

	public SlotType getSlotType(int slot);

	public InventoryType getType();

	public InventoryHolder getHolder();
	
	public void setHolder(InventoryHolder holder);
	
	public void updateSlot(int slot, boolean animation);
	
	public void updateSlots();

	public void updateSlots(Player player);

}
