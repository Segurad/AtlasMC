package de.atlasmc.factory;

import java.io.File;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

@RegistryHolder(key="atlas:factory/chunk_loader_factory")
public interface ChunkLoaderFactory {
	
	ChunkLoader createChunkLoader(World world, File worldDir, ChunkFactory chunkFactory, ConfigurationSection config);

}
