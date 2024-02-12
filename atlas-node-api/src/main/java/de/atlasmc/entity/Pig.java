package de.atlasmc.entity;

public interface Pig extends Animal {
	
	public boolean hasSaddle();
	
	public int getBoostTime();
	
	public void setSaddle(boolean saddle);
	
	public void setBoostTime(int time);

}
