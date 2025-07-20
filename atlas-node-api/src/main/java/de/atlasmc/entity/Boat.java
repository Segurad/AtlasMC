package de.atlasmc.entity;

public interface Boat extends Vehicle {
	
	boolean isLeftPaddleTurning();
	
	void setLeftPaddleTurning(boolean turning);
	
	boolean isRightPaddleTurning();
	
	void setRightPaddleTurning(boolean turning);
	
	int getSplashTimer();

}
