package de.atlasmc.entity;

import de.atlasmc.DyeColor;
import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockFace;

public interface Shulker extends AbstractGolem {
	
	public BlockFace getAttachedFace();
	
	public void setAttachedFace(BlockFace attached);
	
	/**
	 * Returns the Blocks Location this Shulker is attached to or null if non
	 * @return Location or null
	 */
	public Location getAttachedPosition();
	
	/**
	 * Returns the Blocks Location this Shulker is attached to using the given Location or null if non
	 * @param loc
	 * @return Location or null
	 */
	public Location getAttachedPosition(Location loc);
	
	/**
	 * Sets the Blocks position this Shulker is attached to
	 * @param loc or null to reset
	 */
	public void setAttachedPosition(SimpleLocation loc);
	
	public void setAttachedPosition(int x, int y, int z);
	
	public int getAttachedX();
	
	public int getAttachedY();
	
	public int getAttachedZ();
	
	public void setAttachedX(int posX);
	
	public void setAttachedY(int posY);
	
	public void setAttachedZ(int posZ);
	
	public boolean hasAttachedPosition();
	
	public int getShieldHeight();
	
	public void setShieldHeight(int height);
	
	public DyeColor getColor();

	public void setColor(DyeColor color);
	
}
