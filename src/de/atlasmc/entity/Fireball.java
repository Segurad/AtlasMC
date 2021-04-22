package de.atlasmc.entity;

import de.atlasmc.Vector;

public interface Fireball extends Projectile, Explosive {
	
	public Vector getDirection();
	public void setDirection(Vector direction);

}
