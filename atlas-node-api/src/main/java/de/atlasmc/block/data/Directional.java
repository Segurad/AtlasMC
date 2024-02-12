package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.block.BlockFace;

public interface Directional extends BlockData {
	
	public Set<BlockFace> getFaces();
	
	public BlockFace getFacing();
	
	public void setFacing(BlockFace face);

}
