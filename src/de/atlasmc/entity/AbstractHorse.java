package de.atlasmc.entity;

import java.util.UUID;

public interface AbstractHorse extends Animal {
	
	public boolean isTamed();
	
	public void setTamed(boolean tamed);
	
	public boolean isSaddled();
	
	public void setSaddled(boolean saddled);
	
	public boolean hasBred();
	
	public void setBred(boolean bred);
	
	public boolean isEating();
	
	public void setEating(boolean eating);
	
	public boolean isRearing();
	
	public void setRearing(boolean rearing);
	
	public boolean isMouthOpen();
	
	public void setMouthOpen(boolean open);
	
	public UUID getOwner();

	public void setOwner(UUID owner);
	
}
