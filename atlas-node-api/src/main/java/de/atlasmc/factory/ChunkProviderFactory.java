package de.atlasmc.factory;

import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.World;

@RegistryHolder(key="atlas:factory/chunk_provider_factory")
public interface ChunkProviderFactory {
	
	ChunkProvider createProvider(World world, ChunkGenerator generator, ChunkLoader loader, ConfigurationSection config);

}
