package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;

public interface SizedFireball extends AbstractFireball {
	
	public ItemStack getDisplayItem();
	
	public void setDisplayItem(ItemStack item);

}
