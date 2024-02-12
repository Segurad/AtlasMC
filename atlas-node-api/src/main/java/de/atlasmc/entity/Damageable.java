package de.atlasmc.entity;

public interface Damageable extends Entity {
	
	public void damage(double damage);
	
	public double getHealth();
	
	public void setHealth(double health);

}
