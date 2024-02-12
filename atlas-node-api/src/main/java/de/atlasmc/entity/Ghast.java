package de.atlasmc.entity;

public interface Ghast {
	
	public boolean isAttacking();
	
	public void setAttacking(boolean attacking);

	public void setExplosionPower(int power);
	
	/**
	 * Returns the power this Ghast shoots fire balls with
	 * @return power
	 */
	public int getExplosionPower();

}
