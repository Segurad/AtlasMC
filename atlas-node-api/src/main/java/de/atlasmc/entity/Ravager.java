package de.atlasmc.entity;

public interface Ravager extends Raider {

	public void setAttackCooldown(int ticks);
	
	public int getAttackCooldown();
	
	public void setRoarTime(int ticks);
	
	public int getRoarTime();
	
	public void setStunTime(int ticks);
	
	public int getStunTime();

}
