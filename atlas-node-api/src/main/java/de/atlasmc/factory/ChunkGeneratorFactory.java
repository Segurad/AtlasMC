package de.atlasmc.factory;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.World;

@RegistryHolder(key="atlas:factory/chunk_generator_factory")
public interface ChunkGeneratorFactory {
	
	ChunkGenerator createChunkGenerator(World world, ChunkFactory chunkFactory, ConfigurationSection config);

}
