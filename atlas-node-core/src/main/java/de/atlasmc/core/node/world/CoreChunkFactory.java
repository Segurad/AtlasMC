package de.atlasmc.core.node.world;

import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkFactory;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.RegistryValue;

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
