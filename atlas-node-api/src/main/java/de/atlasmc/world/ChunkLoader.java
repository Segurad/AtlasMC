package de.atlasmc.world;

import java.util.Collection;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Represents a instance for loading and saving chunk informations
 */
public interface ChunkLoader {

	/**
	 * Try to load a chunk
	 * (the returned chunk might not be fully loaded use {@link Chunk#isLoaded()})
	 * @param world the chunk will be associated with
	 * @param x position of the chunk
	 * @param z position of the chunk
	 * @return a chunk or null if not present
	 */
	public Future<Chunk> loadChunk(int x, int z);
	
	/**
	 * Try to load all chunks of a region
	 * @param world the chunks will be associated with
	 * @param x position of the region
	 * @param z position of the region
	 * @return list containing all present chunks
	 */
	public Future<Collection<Chunk>> loadRegion(int x, int z);
	
	/**
	 * Saves a chunk with its position
	 * @param chunk that should be saved
	 */
	public void saveChunk(Chunk chunk);

}
