package de.atlasmc.inventory.meta;

import java.util.List;

import de.atlasmc.inventory.ItemStack;

public interface CrossbowMeta extends DamageableMeta {
	
	public CrossbowMeta clone();
	
	@Override
	public default Class<? extends CrossbowMeta> getInterfaceClass() {
		return CrossbowMeta.class;
	}
	
	public void addChargedProjectile(ItemStack item);
	
	public List<ItemStack> getChargedProjectiles();
	
	public boolean hasChargedProjectiles();
	
	public void setChargedProjectiles(List<ItemStack> projectiles);
	
	public boolean isCharged();
	
	public void setCharged(boolean charged);
	
	/**
	 * Returns the number of charged projectiles
	 * @return count
	 */
	public int getProjectileCount();

}
