package de.atlasmc.world;

import de.atlasmc.block.data.BlockData;

public interface ChunkListener {
	
	public void updateBlock(BlockData data, int x, int y, int z);

}
