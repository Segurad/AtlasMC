package de.atlasmc.world;

import java.util.Set;

import de.atlasmc.Tickable;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;

public interface Chunk extends Tickable {

	public Set<BlockData> getPallet();
	public Block getBlockAt(int x, int y, int z);
	public short[] getBlockMapping();
	
}
