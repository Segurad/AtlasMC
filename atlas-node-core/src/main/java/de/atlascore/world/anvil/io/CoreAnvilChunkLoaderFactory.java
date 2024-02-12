package de.atlascore.world.anvil.io;

import java.io.File;

import de.atlasmc.factory.ChunkFactory;
import de.atlasmc.factory.ChunkLoaderFactory;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

@RegistryValue(registry="atlas:factory/chunk_loader_factory", key="atlas-core:factory/anvil_chunk_loader_factory", isDefault = true)
public class CoreAnvilChunkLoaderFactory implements ChunkLoaderFactory {

	@Override
	public ChunkLoader createChunkLoader(World world, File worldDir, ChunkFactory chunkFactory, ConfigurationSection config) {
		return new CoreAnvilChunkLoader(world, worldDir, chunkFactory, config);
	}

}
