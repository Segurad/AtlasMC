package de.atlasmc.node.block.data;

import java.util.Set;

import de.atlasmc.node.block.BlockFace;

public interface MultipleFacing extends BlockData {
	
	Set<BlockFace> getAllowedFaces();
	
	Set<BlockFace> getFaces();
	
	boolean hasFace(BlockFace face);
	
	void setFace(BlockFace face, boolean has);
	
	boolean isValid(BlockFace face);

}
