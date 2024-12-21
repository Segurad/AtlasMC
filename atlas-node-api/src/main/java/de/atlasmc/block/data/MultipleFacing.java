package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.block.BlockFace;

public interface MultipleFacing extends BlockData {
	
	Set<BlockFace> getAllowedFaces();
	
	Set<BlockFace> getFaces();
	
	boolean hasFace(BlockFace face);
	
	void setFace(BlockFace face, boolean has);
	
	boolean isValid(BlockFace face);

}
