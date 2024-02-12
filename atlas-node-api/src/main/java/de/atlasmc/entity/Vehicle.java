package de.atlasmc.entity;

public interface Vehicle extends Entity {
	
	int getShakingPower();
	
	void setShakingPower(int power);
	
	int getShakingDirection();
	
	void setShakingDirection(int direction);
	
	float getShakingMultiplier();
	
	void setShakingMultiplier(float multiplier);

}
