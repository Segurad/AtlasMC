package de.atlasmc.world;

import java.util.Collection;
import java.util.concurrent.Future;

import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;

public interface ChunkProvider extends Tickable {

	@NotNull
	Collection<Entity> getEntities();

	@NotNull
	<T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz);

	@NotNull
	Collection<Entity> getEntitesByClasses(Class<? extends Entity>[] classes);

	int getHighestBlockYAt(int x, int z);

	Block getBlock(int x, int y, int z);

	Entity getEntity(int entityID);

	Chunk getChunk(int x, int z, boolean load);
	
	/**
	 * Loads a chunk async. The future will be invoked sync to the main thread of the server. 
	 * @param x
	 * @param z
	 * @return future
	 */
	@NotNull
	Future<Chunk> getChunkLater(int x, int z);
	
	BlockData getBlockData(int x, int y, int z);
	
	@NotNull
	Collection<Chunk> getChunks();

}
