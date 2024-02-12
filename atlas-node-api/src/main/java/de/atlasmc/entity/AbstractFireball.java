package de.atlasmc.entity;

import org.joml.Vector3d;

public interface AbstractFireball extends Projectile, Explosive {
	
	public Vector3d getDirection();
	
	public void setDirection(Vector3d direction);

	public void setDirection(double x, double y, double z);

	public Vector3d getSpeed();
	
	public void setSpeed(Vector3d speed);
	
	public void setSpeed(double x, double y, double z);

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this Arrow despawns or -1 if not counting
	 * @return ticks or -1
	 */
	public int getLifeTime();
	
}
