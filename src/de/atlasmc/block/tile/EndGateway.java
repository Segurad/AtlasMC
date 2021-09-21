package de.atlasmc.block.tile;

import de.atlasmc.Location;

public interface EndGateway {

	public long getAge();
	
	public void setAge(long age);
	
	/**
	 * Returns whether or not the teleport should be direct at the exit portal or with a random offset 
	 * @return true if it is exact
	 */
	public boolean isExactTeleport();
	
	public void setExactTeleport(boolean exact);
	
	/**
	 * Returns whether or not the exit portal coordinates are used as relative coordinates from this tile
	 * @return true if the coordinates are handled as relative
	 */
	public boolean isRelativeCoordinates();
	
	public void setRelativeCoordinates(boolean relative);
	
	public Location getExitPortal();
	
	public void setExitPortal(Location loc);
	
}
