package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.inventory.ItemStack;

public interface Item extends Entity {
	
	public ItemStack getItemStack();
	
	public void setItemStack(ItemStack item);
	
	/**
	 * Returns the time in ticks until this item is pickupable or -1 if generally not.<br>
	 * Will be remain at 0 if it is pickupable
	 * @return ticks or -1
	 */
	public int getPickupDelay();
	
	public void setPickupDelay(int delay);
	
	/**
	 * Returns the {@link UUID} of the Entity that is capable of picking up this item or null if any Entity can
	 * @return uuid or null
	 */
	public UUID getOwner();
	
	public void setOwner(UUID uuid);
	
	public UUID getThrower();
	
	public void setThrower(UUID uuid);
	
	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this item despawns or -1 if it does not despawn
	 * @return ticks or -1
	 */
	public int getLifeTime();

}
