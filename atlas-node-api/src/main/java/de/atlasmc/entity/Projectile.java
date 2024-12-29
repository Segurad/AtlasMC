package de.atlasmc.entity;

import de.atlasmc.ProjectileSource;

public interface Projectile extends Entity {
	
	ProjectileSource getShooter();
	
	boolean doesBounce();
	
	void setShooter(ProjectileSource source);
	
	void setBounce(boolean bounce);
	
}
