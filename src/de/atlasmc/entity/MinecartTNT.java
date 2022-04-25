package de.atlasmc.entity;

public interface MinecartTNT extends AbstractMinecart {

	public void setFuseTime(int ticks);
	
	/**
	 * Returns the time in ticks until the TNT explodes or -1 if it is not fusing
	 * @return ticks
	 */
	public int getFuseTime();

}
