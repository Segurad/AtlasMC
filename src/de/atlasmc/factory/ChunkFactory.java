package de.atlasmc.factory;

import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

/**
 * Factory for creating {@link Chunk}s
 * Used within {@link ChunkLoader} and {@link ChunkGenerator}
 */
public interface ChunkFactory {
	
	public static ChunkFactory DEFAULT_FACTORY = null;
	
	public Chunk createChunk(World world, int x, int z);

}
