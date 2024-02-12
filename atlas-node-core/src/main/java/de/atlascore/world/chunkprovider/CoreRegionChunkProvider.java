package de.atlascore.world.chunkprovider;

import java.util.Collection;

import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

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
