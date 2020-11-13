package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;

public interface ThrowableProjectile extends Projectile {

	public ItemStack getItem();
	public void setItem(ItemStack item);
}
