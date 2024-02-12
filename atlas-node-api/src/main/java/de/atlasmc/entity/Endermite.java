package de.atlasmc.entity;

public interface Endermite extends Monster {

	/**
	 * Sets the time in tick this Endermite will be alive or -1 for infinite time
	 * @param lifetime
	 */
	public void setLifetime(int lifetime);

	/**
	 * Returns the time in ticks this Endermite will be alive.<br>
	 * Will be -1 for infinite time
	 * @return ticks or -1
	 */
	public int getLifetime();

	public void setPlayerSpawned(boolean playerSpawned);
	
	public boolean isPlayerSpawned();
	
}
