package de.atlasmc.entity;

public interface AbstractArrow extends Projectile {
	
	public boolean isCritical();
	
	public boolean isInBlock();
	
	public void setCritical(boolean critical);

	public int getPiercingLevel();
	
	/**
	 * Sets the piercing level of this projectile (values higher than 0xFF are ignored)
	 * @param level of piercing
	 */
	public void setPiercingLevel(int level);
}
