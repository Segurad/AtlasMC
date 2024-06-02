package de.atlascore.world;

import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.world.ChunkFactory;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkGeneratorFactory;
import de.atlasmc.world.World;

public class CoreFlatworldChunkGeneratorFactory implements ChunkGeneratorFactory {

	@Override
	public ChunkGenerator createChunkGenerator(World world, ChunkFactory chunkFactory, ConfigurationSection config) {
		return new CoreFlatworldChunkGenerator(world, chunkFactory, config);
	}

}
