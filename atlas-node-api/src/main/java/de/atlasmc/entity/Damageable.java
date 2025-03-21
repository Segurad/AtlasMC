package de.atlasmc.entity;

public interface Damageable extends Entity {
	
	void damage(double damage);
	
	double getHealth();
	
	void setHealth(double health);

}
