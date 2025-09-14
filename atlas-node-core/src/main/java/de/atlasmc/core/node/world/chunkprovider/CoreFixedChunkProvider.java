package de.atlasmc.core.node.world.chunkprovider;

import java.util.Collection;

import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkGenerator;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.World;

/**
 * ChunkProvider for quicker chunk access but no dynamic world size<br>
 * Stores all chunks in a defined area with calculable access
 */
public class CoreFixedChunkProvider extends CoreAbstractChunkProvider {

	public CoreFixedChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
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
