package de.atlasmc.entity;

import de.atlasmc.Vector;

public interface AbstractFireball extends Projectile, Explosive {
	
	public Vector getDirection();
	
	public void setDirection(Vector direction);

	public void setDirection(double x, double y, double z);

	public Vector getSpeed();
	
	public void setSpeed(Vector speed);
	
	public void setSpeed(double x, double y, double z);

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this Arrow despawns or -1 if not counting
	 * @return ticks or -1
	 */
	public int getLifeTime();
	
}
