package de.atlasmc.entity;

public interface FishingHook extends Entity {
	
	Entity getHookedEntity();

	void setHookedEntity(Entity hooked);

}
