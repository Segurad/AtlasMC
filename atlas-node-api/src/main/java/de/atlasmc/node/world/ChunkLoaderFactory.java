package de.atlasmc.node.world;

import java.io.File;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key="atlas:factory/chunk_loader_factory")
public interface ChunkLoaderFactory {
	
	ChunkLoader createChunkLoader(World world, File worldDir, ChunkFactory chunkFactory, ConfigurationSection config);

}
