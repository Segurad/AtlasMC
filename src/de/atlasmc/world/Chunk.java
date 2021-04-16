package de.atlasmc.world;

import java.util.List;
import de.atlasmc.block.Block;

public interface Chunk {

	public List<ChunkSection> getSections();
	public ChunkSection getSection(int hight);
	public Block getBlockAt(int x, int y, int z);
	public World getWorld();
	public boolean isLoaded();
	public void load();
	
}
