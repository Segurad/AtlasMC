package de.atlasmc.inventory;

import de.atlasmc.entity.HumanEntity;

public interface PlayerInventory extends Inventory {
	
	public HumanEntity getHolder();

	public ItemStack getItemInMainHand();

	public ItemStack getItemInOffHand();

	public int getHeldItemSlot();
	
	public void setHeldItemSlot(int slot);

}
