package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Tickable;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;

public interface Chunk extends Tickable {

	public List<BlockData> getPallet();
	public Block getBlockAt(int x, int y, int z);
	
}
