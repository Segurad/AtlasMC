package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.FireworksComponent;

public interface FireworkRocket extends Projectile {
	
	FireworksComponent getFireworkMeta();
	
	ItemStack getFirework();
	
	boolean isShotAtAngle();
	
	void detonate();
	
	void setFireworkMeta(FireworksComponent meta);
	
	void setFirework(ItemStack firework);
	
	void setShotAtAngle(boolean shotAtAngle);

	void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks this rocket is fling
	 * @return ticks
	 */
	int getLifeTime();
	
	void setMaxLifeTime(int ticks);

	/**
	 * Returns the tick this rocket should explode.<br>
	 * Counted by life time
	 * @return max life time
	 */
	int getMaxLifeTime();
	
}
