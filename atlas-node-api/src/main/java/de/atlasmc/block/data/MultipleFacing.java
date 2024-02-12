package de.atlasmc.block.data;

import java.util.Set;

import de.atlasmc.block.BlockFace;

public interface MultipleFacing extends BlockData {
	
	public Set<BlockFace> getAllowedFaces();
	public Set<BlockFace> getFaces();
	public boolean hasFace(BlockFace face);
	public void setFace(BlockFace face, boolean has);
	public boolean isValid(BlockFace face);

}
