package de.atlasmc.core.node.world;

import de.atlasmc.node.world.ChunkFactory;
import de.atlasmc.node.world.ChunkGenerator;
import de.atlasmc.node.world.ChunkGeneratorFactory;
import de.atlasmc.node.world.World;
import de.atlasmc.util.configuration.ConfigurationSection;

public class CoreFlatworldChunkGeneratorFactory implements ChunkGeneratorFactory {

	@Override
	public ChunkGenerator createChunkGenerator(World world, ChunkFactory chunkFactory, ConfigurationSection config) {
		return new CoreFlatworldChunkGenerator(world, chunkFactory, config);
	}

}
