package de.atlasmc.inventory;

import de.atlasmc.entity.HumanEntity;

public interface PlayerInventory extends Inventory {
	
	ItemStack getHelmet();
	
	ItemStack getChestplate();
	
	ItemStack getLeggings();
	
	ItemStack getBoots();
	
	void setHelmet(ItemStack item);
	
	void setChestplate(ItemStack item);
	
	void setLeggings(ItemStack item);
	
	void setBoots(ItemStack item);
	
	ItemStack[] getArmorContents();
	
	ItemStack[] getArmorContentsUnsafe();
	
	void setArmorContents(ItemStack[] items);
	
	void setArmorContentsUnsafe(ItemStack[] items);
	
	HumanEntity getHolder();

	ItemStack getItemInMainHand();
	
	void setItemInMainHand(ItemStack item);

	ItemStack getItemInOffHand();
	
	void setItemInOffHand(ItemStack item);

	int getHeldItemSlot();
	
	void setHeldItemSlot(int slot);
	
	CraftingInventory getCraftingInventory();

}
