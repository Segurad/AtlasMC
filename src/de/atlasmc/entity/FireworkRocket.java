package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.meta.FireworkMeta;

public interface FireworkRocket extends Projectile {
	
	public FireworkMeta getFireworkMeta();
	
	public ItemStack getFirework();
	
	public boolean isShotAtAngle();
	
	public void detonate();
	
	public void setFireworkMeta(FireworkMeta meta);
	
	public void setFirework(ItemStack firework);
	
	public void setShotAtAngle(boolean shotAtAngle);

}
