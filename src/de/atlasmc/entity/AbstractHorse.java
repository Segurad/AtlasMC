package de.atlasmc.entity;

import java.util.UUID;

public interface AbstractHorse extends Animal {
	
	public boolean isUsed();
	public boolean isTame();
	public boolean isSaddled();
	public boolean hasBred();
	public boolean isEating();
	public boolean isRearing();
	public boolean isMouthOpen();
	public UUID getOwner();

}
