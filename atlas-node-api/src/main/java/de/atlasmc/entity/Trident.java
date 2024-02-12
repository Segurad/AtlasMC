package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;

public interface Trident extends AbstractArrow {
	
	public int getLoyalityLevel();
	
	public void setLoyalityLevel(int level);
	
	public boolean hasEnchantmentGlint();

	public void setEnchantmentGlint(boolean glint);

	public void setItem(ItemStack item);
	
	public ItemStack getItem();
	
	public boolean hasItem();
	
}
