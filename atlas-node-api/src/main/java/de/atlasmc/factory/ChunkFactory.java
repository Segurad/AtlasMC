package de.atlasmc.factory;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

/**
 * Factory for creating {@link Chunk}s
 * Used within {@link ChunkLoader} and {@link ChunkGenerator}
 */
@RegistryHolder(key="atlas:factory/chunk_factory")
public interface ChunkFactory {
	
	/**
	 * Creates a chunk for the given world using the provided chunk coordinates
	 * @param world the world this chunk belongs to
	 * @param x coordinate of the chunk
	 * @param z coordinate of the chunk
	 * @return a new chunk
	 */
	Chunk createChunk(World world, int x, int z);

}
