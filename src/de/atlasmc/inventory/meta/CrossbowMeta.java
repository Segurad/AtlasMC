package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public interface CrossbowMeta extends DamageableMeta {
	
	public void addChargedProjectile(ItemStack item);
	public List<ItemStack> getChargedProjectiles();
	public boolean hasChargedProjectiles();
	public void setChargedProjectiles(List<ItemStack> projectiles);

}
