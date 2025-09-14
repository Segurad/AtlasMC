package de.atlasmc.node.world;

import java.util.Collection;
import java.util.concurrent.Future;

import de.atlasmc.node.block.Block;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;

public interface ChunkProvider extends Tickable {

	int getHighestBlockYAt(int x, int z);

	Block getBlock(int x, int y, int z);

	Chunk getChunk(int x, int z, boolean load);
	
	/**
	 * Loads a chunk async. The future will be invoked sync to the main thread of the server. 
	 * @param x
	 * @param z
	 * @return future
	 */
	@NotNull
	Future<Chunk> getChunkLater(int x, int z);
	
	BlockData getBlockData(int x, int y, int z);
	
	@NotNull
	Collection<Chunk> getChunks();

}
