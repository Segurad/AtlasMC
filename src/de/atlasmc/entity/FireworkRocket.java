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

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks this rocket is fling
	 * @return ticks
	 */
	public int getLifeTime();
	
	public void setMaxLifeTime(int ticks);

	/**
	 * Returns the tick this rocket should explode.<br>
	 * Counted by life time
	 * @return max life time
	 */
	public int getMaxLifeTime();
	
}
