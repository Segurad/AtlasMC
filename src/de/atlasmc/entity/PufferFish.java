package de.atlasmc.entity;

public interface PufferFish extends Fish {
	
	public int getPuffState();
	
	/**
	 * Sets the puff state between 0 and 2
	 * @param state
	 */
	public void setPuffState(int state);

}
