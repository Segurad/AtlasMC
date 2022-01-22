package de.atlasmc.entity;

public interface Zombie extends Monster {
	
	public boolean isBaby();
	
	public boolean isBecomingDrowned();

	public void setBaby(boolean baby);
	
	public void setBecomingDorwned(boolean drowned);
	
}
