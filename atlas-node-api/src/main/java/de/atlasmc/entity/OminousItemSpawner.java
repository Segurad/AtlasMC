package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;

public interface OminousItemSpawner extends Entity {
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	long getSpawnTicks();
	
	void setSpawnTicks(long ticks);

}
