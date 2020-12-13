package de.atlasmc.entity;

import de.atlasmc.inventory.meta.FireworkMeta;

public interface FireworkRocket extends Projectile {
	
	public FireworkMeta getFireworkMeta();
	public boolean isShotAtAngle();
	public void detonate();
	public void setFireworkMeta();
	public void setShotAtAngle(boolean shotAtAngle);

}
