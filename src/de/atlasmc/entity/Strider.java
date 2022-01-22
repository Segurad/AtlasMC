package de.atlasmc.entity;

public interface Strider extends Animal {
	
	public int getBoostTime();
	
	public void setBoostTime(int time);
	
	public boolean isShaking();
	
	public void setShaking(boolean shaking);
	
	public boolean hasSaddle();

	public void setSaddle(boolean saddle);
	
}
