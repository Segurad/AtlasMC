package de.atlasmc.entity;

import java.util.UUID;

public interface Fox extends Animal {
	
	public Type getFoxType();
	
	public void setFoxType(Type type);
	
	public boolean isSitting();
	
	public void setSitting(boolean sitting);
	
	public boolean isInterested();
	
	public void setInterested(boolean interested);
	
	public boolean isPouncing();
	
	public void setPouncing(boolean pouncing);
	
	public boolean isSleeping();
	
	public void setSleeping(boolean sleeping);
	
	public boolean isFaceplanted();
	
	public void setFaceplanted(boolean faceplanted);
	
	public boolean isDefending();
	
	public void setDefending(boolean defending);
	
	public UUID getFirstTrusted();
	
	public void setFirstTrusted(UUID uuid);
	
	public UUID getSecondTrusted();
	
	public void setSecondTrusted(UUID uuid);
	
	public boolean isCrouching();
	
	public void setCrouching(boolean crouching);
	
	public static enum Type {
		RED,
		SNOW;

		public static Type getByID(int id) {
			switch (id) {
			case 0:
				return RED;
			case 1:
				return SNOW;
			default:
				return RED;
			}
		}

		public int getID() {
			return ordinal();
		}
	}

}
