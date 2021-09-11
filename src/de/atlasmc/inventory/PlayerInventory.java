package de.atlasmc.inventory;

import de.atlasmc.entity.HumanEntity;

public interface PlayerInventory extends Inventory {
	
	public ItemStack getHelmet();
	
	public ItemStack getChestplate();
	
	public ItemStack getLeggings();
	
	public ItemStack getBoots();
	
	public void setHelmet(ItemStack item);
	
	public void setChestplate(ItemStack item);
	
	public void setLeggings(ItemStack item);
	
	public void setBoots(ItemStack item);
	
	public HumanEntity getHolder();

	public ItemStack getItemInMainHand();
	
	public void setItemInMainHand(ItemStack item);

	public ItemStack getItemInOffHand();
	
	public void setItemInOffHand(ItemStack item);

	public int getHeldItemSlot();
	
	public void setHeldItemSlot(int slot);
	
	public CraftingInventory getCraftingInventory();

}
