package de.atlasmc.node.world;

import de.atlasmc.registry.RegistryHolder;

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
