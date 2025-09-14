package de.atlasmc.core.node.world.chunkprovider;

import java.util.Collection;

import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkGenerator;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.World;

/**
 * ChunkProvider implementation that mixes flexibility and speed by storing regions of chunks in a buffer.
 * The chunk region is accessed by iterating through the buffer and the chunk is then access by calculating the position in the region.
 * @see {@link CoreBufferedChunkProvider} and {@link CoreFixedChunkProvider}
 */
public class CoreRegionChunkProvider extends CoreAbstractChunkProvider {

	public CoreRegionChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
		super(world, generator, loader);
	}

	@Override
	public Collection<Chunk> getChunks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Chunk getCachedChunk(long pos, int x, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addCachedChunk(long pos, Chunk chunk) {
		// TODO Auto-generated method stub
		
	}

}
