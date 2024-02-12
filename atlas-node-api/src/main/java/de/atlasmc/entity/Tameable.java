package de.atlasmc.entity;

import java.util.UUID;

public interface Tameable extends Animal {
	
	public boolean isSitting();
	
	public void setSitting(boolean sitting);
	
	public boolean isTamed();
	
	public void setTamed(boolean tamed);
	
	public UUID getOwner();
	
	public void setOwner(UUID owner);

}
