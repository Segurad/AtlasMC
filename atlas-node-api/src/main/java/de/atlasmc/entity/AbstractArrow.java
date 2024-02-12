package de.atlasmc.entity;

public interface AbstractArrow extends Projectile {
	
	public boolean isCritical();
	
	public void setCritical(boolean critical);

	public int getPiercingLevel();
	
	/**
	 * Sets the piercing level of this projectile (values higher than 0xFF are ignored)
	 * @param level of piercing
	 */
	public void setPiercingLevel(int level);

	public void setDamage(double damage);
	
	public double getDamage();

	public void setShakeOnImpact(boolean shake);
	
	public boolean isShakingOnImpact();

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this Arrow despawns or -1 if not counting
	 * @return ticks or -1
	 */
	public int getLifeTime();

	public void setPickupable(boolean pickupable);
	
	public boolean isPickupable();
	
}
