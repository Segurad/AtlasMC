package de.atlasmc.entity;

public interface MinecartFurnace extends AbstractMinecart {
	
	public boolean hasFuel();
	
	public void setFuel(boolean fuel);
	
	public void setFuelLevel(int ticks);
	
	/**
	 * Returns the time in tick until this minecart runs out of fuel or -1 if no time is counted
	 * @return ticks or -1
	 */
	public int getFuelLevel();

}
