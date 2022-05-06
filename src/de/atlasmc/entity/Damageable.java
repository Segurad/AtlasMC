package de.atlasmc.entity;

public interface Damageable extends Entity {
	
	public void damage(double damage);
	
	public float getHealth();
	
	public void setHealth(float health);

}
