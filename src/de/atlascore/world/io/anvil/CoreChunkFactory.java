package de.atlascore.world.io.anvil;

import de.atlascore.world.CoreChunk;
import de.atlasmc.factory.ChunkFactory;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

/**
 * Core implementation of {@link ChunkFactory} for construction of {@link CoreChunk}
 */
public class CoreChunkFactory implements ChunkFactory {
	
	public static final ChunkFactory INSTANCE = new CoreChunkFactory();
	
	private CoreChunkFactory() {}

	@Override
	public Chunk createChunk(World world, int x, int z) {
		return new CoreChunk(world, x, z);
	}

}
