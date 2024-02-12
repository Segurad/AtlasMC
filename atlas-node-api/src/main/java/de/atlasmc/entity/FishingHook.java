package de.atlasmc.entity;

public interface FishingHook extends Projectile {
	
	public Entity getHookedEntity();
	
	public void setHookedEntity(Entity hooked);

}
