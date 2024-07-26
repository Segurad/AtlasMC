package de.atlascore.world.chunkprovider;

import de.atlasmc.registry.RegistryValue;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.ChunkProviderFactory;
import de.atlasmc.world.World;

@RegistryValue(registry="atlas:factory/chunk_provider_factory", key="atlas-core:buffered_chunk_provider", isDefault = true)
public class CoreBufferedChunkProviderFactory implements ChunkProviderFactory {

	@Override
	public ChunkProvider createProvider(World world, ChunkGenerator generator, ChunkLoader loader, ConfigurationSection config) {
		return new CoreBufferedChunkProvider(world, generator, loader);
	}
	
}
