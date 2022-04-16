package de.atlasmc.entity;

public interface Zombie extends Monster {
	
	public boolean isBaby();
	
	public boolean isBecomingDrowned();

	public void setBaby(boolean baby);
	
	public void setBecomingDorwned(boolean drowned);

	public void setCanBreakDoors(boolean breakDoor);
	
	public boolean canBreakDoors();

	public void setDrownedConversionTime(int ticks);
	
	/**
	 * Returns the ticks in which this Zombie will be converted to a Drowned.<br>
	 * Will be -1 if no conversion is happening
	 * @return ticks or -1
	 */
	public int getDrownedConverionTime();
	
}
