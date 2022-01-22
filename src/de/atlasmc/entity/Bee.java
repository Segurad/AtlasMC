package de.atlasmc.entity;

public interface Bee extends Animal {

	public boolean isAngry();
	
	public void setAngry(boolean angry);
	
	public boolean hasStung();
	
	public void setStung(boolean stung);
	
	public boolean hasNectar();
	
	public void setNectar(boolean nectar);
	
	public int getAnger();

	public void setAnger(int ticks);
	
	public int getTicksInHive();
	
	public int getHiveMinOccupationTicks();
	
	public void setTicksInHive(int ticks);
	
	public void setHiveMinOccupationTicks(int ticks);
	
}
