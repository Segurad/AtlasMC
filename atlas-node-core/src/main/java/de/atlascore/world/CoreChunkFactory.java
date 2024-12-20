package de.atlascore.world;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkFactory;
import de.atlasmc.world.World;

/**
 * Core implementation of {@link ChunkFactory} for construction of {@link CoreChunk}
 */
@RegistryValue(registry="atlas:factory/chunk_factory", key="atlas-core:chunk", isDefault = true)
public class CoreChunkFactory implements ChunkFactory {

	@Override
	public Chunk createChunk(World world, int x, int z) {
		return new CoreChunk(world, x, z);
	}

}
