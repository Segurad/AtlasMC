package de.atlasmc.world;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;

@RegistryHolder(key="atlas:factory/chunk_provider_factory")
public interface ChunkProviderFactory {
	
	ChunkProvider createProvider(World world, ChunkGenerator generator, ChunkLoader loader, ConfigurationSection config);

}
