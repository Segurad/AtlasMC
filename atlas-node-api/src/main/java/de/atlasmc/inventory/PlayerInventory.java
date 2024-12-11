package de.atlasmc.inventory;

import de.atlasmc.entity.HumanEntity;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface PlayerInventory extends Inventory {
	
	ItemStack getHelmet();
	
	ItemStack getChestplate();
	
	ItemStack getLeggings();
	
	ItemStack getBoots();
	
	void setHelmet(ItemStack item);
	
	void setChestplate(ItemStack item);
	
	void setLeggings(ItemStack item);
	
	void setBoots(ItemStack item);
	
	@UnsafeAPI
	ItemStack getHelmetUnsafe();
	
	@UnsafeAPI
	ItemStack getChestplateUnsafe();
	
	@UnsafeAPI
	ItemStack getLeggingsUnsafe();
	
	@UnsafeAPI
	ItemStack getBootsUnsafe();
	
	@UnsafeAPI
	void setHelmetUnsafe(ItemStack item);

	@UnsafeAPI
	void setChestplateUnsafe(ItemStack item);
	
	@UnsafeAPI
	void setLeggingsUnsafe(ItemStack item);
	
	@UnsafeAPI
	void setBootsUnsafe(ItemStack item);
	
	ItemStack[] getArmorContents();
	
	@UnsafeAPI
	ItemStack[] getArmorContentsUnsafe();
	
	void setArmorContents(ItemStack[] items);
	
	@UnsafeAPI
	void setArmorContentsUnsafe(ItemStack[] items);
	
	HumanEntity getHolder();

	ItemStack getItemInMainHand();
	
	void setItemInMainHand(ItemStack item);

	ItemStack getItemInOffHand();
	
	void setItemInOffHand(ItemStack item);
	
	@UnsafeAPI
	ItemStack getItemInMainHandUnsafe();
	
	@UnsafeAPI
	void setItemInMainHandUnsafe(ItemStack item);

	@UnsafeAPI
	ItemStack getItemInOffHandUnsafe();
	
	@UnsafeAPI
	void setItemInOffHandUnsafe(ItemStack item);

	int getHeldItemSlot();
	
	void setHeldItemSlot(int slot);
	
	CraftingInventory getCraftingInventory();

}
