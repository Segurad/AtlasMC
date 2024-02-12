package de.atlasmc.entity;

public interface Creeper extends Monster {
	
	/**
	 * Returns the time in ticks until the {@link Creeper} explodes or -1 if not fusing
	 * @return time or -1
	 */
	public int getFuseTime();
	
	/**
	 * Sets the time till the {@link Creeper} explodes. Use -1 to reset
	 * @param fuze
	 */
	public void setFuseTime(int fuze);
	
	public boolean isChared();
	
	public void setChared(boolean charged);
	
	public boolean isIgnited();
	
	public void setIgnited(boolean ignited);

	/**
	 * Returns whether or not the {@link Creeper} is displayed as fusing
	 * @return true if fusing
	 */
	public boolean isFusing();
	
	/**
	 * Sets whether or no the {@link Creeper} should be displayed as fusing
	 * @param fuzing
	 */
	public void setFusing(boolean fuzing);

	/**
	 * Sets the radius of explosion. Values lower or equal to 0 will result in no destruction.
	 * @param radius
	 */
	public void setExplosionRadius(int radius);
	
	public int getExplosionRadius();
	
}
