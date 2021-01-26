package de.atlasmc.entity;

import de.atlasmc.DyeColor;
import de.atlasmc.Location;
import de.atlasmc.block.BlockFace;

public interface Shulker extends AbstractGolem {
	
	public BlockFace getAttachedFace();
	public Location getAttachmentPosition();
	public int getShieldHeight();
	public DyeColor getColor();

}
