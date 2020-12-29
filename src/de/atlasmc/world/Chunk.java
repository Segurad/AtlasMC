package de.atlasmc.world;

import java.util.List;
import de.atlasmc.Tickable;
import de.atlasmc.block.Block;

public interface Chunk extends Tickable {

	public List<ChunkSection> getSections();
	public ChunkSection getSection(int hight);
	public Block getBlockAt(int x, int y, int z);
	
}
