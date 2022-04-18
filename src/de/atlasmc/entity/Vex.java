package de.atlasmc.entity;

public interface Vex extends Monster {
	
	public boolean isAttacking();
	
	public void setAttacking(boolean attacking);

	public void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this vex takes damage.<br>
	 * After it took damage the time will be set to 20.<br>
	 * Will be -1 if not counting
	 * @return ticks or -1
	 */
	public int getLifeTime();

}
