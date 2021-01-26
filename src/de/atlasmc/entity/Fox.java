package de.atlasmc.entity;

import java.util.UUID;

public interface Fox extends Animal {
	
	public Type getFoxType();
	public boolean isSitting();
	public boolean isInterested();
	public boolean isPouncing();
	public boolean isSleeping();
	public boolean isFaceplanted();
	public boolean isDefending();
	public UUID getFirstTrusted();
	public UUID getSecondTrusted();
	
	public static enum Type {
		RED,
		SNOW
	}

}
