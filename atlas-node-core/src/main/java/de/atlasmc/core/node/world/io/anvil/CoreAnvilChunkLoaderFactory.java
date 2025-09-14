package de.atlasmc.core.node.world.io.anvil;

import java.io.File;

import de.atlasmc.node.world.ChunkFactory;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.ChunkLoaderFactory;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry="atlas:factory/chunk_loader_factory", key="atlas-core:factory/anvil_chunk_loader_factory", isDefault = true)
public class CoreAnvilChunkLoaderFactory implements ChunkLoaderFactory {

	@Override
	public ChunkLoader createChunkLoader(World world, File worldDir, ChunkFactory chunkFactory, ConfigurationSection config) {
		return new CoreAnvilChunkLoader(world, worldDir, chunkFactory, config);
	}

}
