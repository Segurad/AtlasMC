package de.atlasmc.node.block.data;

import java.util.Set;

import de.atlasmc.node.block.BlockFace;
import de.atlasmc.util.annotation.NotNull;

public interface Directional extends BlockData {
	
	@NotNull
	Set<BlockFace> getFaces();
	
	@NotNull
	BlockFace getFacing();
	
	void setFacing(@NotNull BlockFace face);

}
