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
	public Location getAttachmentPosition();
	
	/**
	 * Returns the Blocks Location this Shulker is attached to using the given Location or null if non
	 * @param loc
	 * @return Location or null
	 */
	public Location getAttachmentPosition(Location loc);
	
	/**
	 * Sets the Blocks position this Shulker is attached to
	 * @param loc or null to reset
	 */
	public void setAttachmentPosition(SimpleLocation loc);
	
	public void setAttachmentPosition(int x, int y, int z);
	
	public int getShieldHeight();
	
	public void setShieldHeight(int height);
	
	public DyeColor getColor();

	public void setColor(DyeColor color);
	
}
