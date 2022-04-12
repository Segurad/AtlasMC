package de.atlasmc.entity;

import de.atlasmc.block.BlockFace;

public interface Hanging extends Entity {
	
	public BlockFace getAttachedFace();
	
	public void setFacingDirection(BlockFace face);

}
