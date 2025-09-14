package de.atlasmc.node.world;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key="atlas:factory/chunk_generator_factory")
public interface ChunkGeneratorFactory {
	
	ChunkGenerator createChunkGenerator(World world, ChunkFactory chunkFactory, ConfigurationSection config);

}
