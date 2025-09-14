package de.atlasmc.core.node.world.chunkprovider;

import de.atlasmc.node.world.ChunkGenerator;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.ChunkProvider;
import de.atlasmc.node.world.ChunkProviderFactory;
import de.atlasmc.node.world.World;
import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryValue(registry="atlas:factory/chunk_provider_factory", key="atlas-core:buffered_chunk_provider", isDefault = true)
public class CoreBufferedChunkProviderFactory implements ChunkProviderFactory {

	@Override
	public ChunkProvider createProvider(World world, ChunkGenerator generator, ChunkLoader loader, ConfigurationSection config) {
		return new CoreBufferedChunkProvider(world, generator, loader);
	}
	
}
