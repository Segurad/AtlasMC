package de.atlasmc.block.data;

import de.atlasmc.block.BlockFace;

public interface Rotatable extends BlockData {
	
	public BlockFace getRotation();
	public void setRotation(BlockFace rotation);

}
