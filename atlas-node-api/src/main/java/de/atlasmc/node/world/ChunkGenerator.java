package de.atlasmc.node.world;

import de.atlasmc.util.concurrent.future.Future;

public interface ChunkGenerator {
	
	public Future<Chunk> generate(int x, int z);

}
