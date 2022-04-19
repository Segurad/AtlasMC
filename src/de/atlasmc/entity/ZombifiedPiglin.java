package de.atlasmc.entity;

public interface ZombifiedPiglin extends Zombie {

	public void setAngerTime(int ticks);
	
	public int getAngerTime();
	
	public boolean isAngry();
	
	public void setAngry(boolean angry);

}
