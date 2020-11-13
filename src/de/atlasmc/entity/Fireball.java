package de.atlasmc.entity;

import de.atlasmc.Vector;
import de.atlasmc.io.pack.Explosive;

public interface Fireball extends Projectile, Explosive {
	
	public Vector getDirection();
	public void setDirection(Vector direction);

}
