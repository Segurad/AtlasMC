package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.inventory.ItemStack;

public interface Item extends Entity {
	
	public ItemStack getItemStack();
	public void setItemStack(ItemStack item);
	public int getPickupDelay();
	public void setPickupDelay(int delay);
	public UUID getOwner();
	public void setOwner(UUID uuid);
	public UUID getThrower();
	public void setThrower(UUID uuid);

}
