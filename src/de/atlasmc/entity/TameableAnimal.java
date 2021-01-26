package de.atlasmc.entity;

import java.util.UUID;

public interface TameableAnimal extends Animal {
	
	public boolean isSitting();
	public boolean isTamed();
	public UUID getOwner();

}
