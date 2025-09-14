package de.atlasmc.node.block.data;

import de.atlasmc.node.block.BlockFace;

public interface Rotatable extends BlockData {
	
	public BlockFace getRotation();
	public void setRotation(BlockFace rotation);

}
