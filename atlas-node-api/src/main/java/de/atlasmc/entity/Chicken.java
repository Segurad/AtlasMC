package de.atlasmc.entity;

public interface Chicken extends Animal {

	public void setEggLayTime(int time);
	
	/**
	 * Returns time in ticks until the Chicken lays a egg or -1 if no active timer
	 * @return ticks or -1
	 */
	public int getEggLayTime();

}
