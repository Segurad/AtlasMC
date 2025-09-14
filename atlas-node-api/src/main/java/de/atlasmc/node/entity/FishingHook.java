package de.atlasmc.node.entity;

public interface FishingHook extends Entity {
	
	Entity getHookedEntity();

	void setHookedEntity(Entity hooked);

}
