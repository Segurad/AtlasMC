package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;

public interface SizedFireball extends Fireball {
	
	public ItemStack getDisplayItem();
	public void setDisplayItem();

}
