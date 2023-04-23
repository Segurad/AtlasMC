package de.atlascore.world.io.anvil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import de.atlasmc.factory.ChunkFactory;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

/**
 * Core implementation of {@link ChunkLoader} for the anvil chunk format
 */
public class CoreAnvilChunkLoader implements ChunkLoader {
	
	private static final int CACHE_SIZE = 5;
	
	private final File worldDir;
	private final ChunkFactory chunkFactory;
	private final CoreAnvilRegionFile[] cache;
	/**
	 * Index of the last set element in the region cache
	 */
	private int cacheIndex = 0;
	
	public CoreAnvilChunkLoader(File worldDir, ChunkFactory factory) {
		if (worldDir.exists()) {
			if (!worldDir.isDirectory())
				throw new IllegalArgumentException("Given world directory is not a directory: " + worldDir.getAbsolutePath());
			if (!worldDir.canRead())
				throw new IllegalArgumentException("Unable to read from world directory!");
		}
		if (factory == null)
			throw new IllegalArgumentException("ChunkFactory can not be null!");
		this.worldDir = worldDir;
		this.chunkFactory = factory;
		cache = new CoreAnvilRegionFile[CACHE_SIZE];
	}

	@Override
	public Chunk loadChunk(World world, int x, int z) {
		
		return null;
	}

	@Override
	public void saveChunk(Chunk chunk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Chunk> loadRegion(World world, int x, int z) {
		CoreAnvilRegionFile regionFile = getRegionFile(world, x, z);
		List<Chunk> chunks = null;
		try {
			chunks = regionFile.loadChunks();
		} catch (IOException e) {
			world.getServer().getLogger().error("Error while loading Region", e);
		}
		return chunks;
	}
	
	/**
	 * Returns the {@link CoreAnvilRegionFile} matching the given position
	 * @param world
	 * @param x position of the region 
	 * @param z position of the region
	 * @return the {@link CoreAnvilRegionFile}
	 */
	private synchronized CoreAnvilRegionFile getRegionFile(World world, int x, int z) {
		for (int i = cacheIndex, j = 0; j < CACHE_SIZE; j++, i = (i + 1) % CACHE_SIZE) {
			CoreAnvilRegionFile r = cache[i];
			if (r == null)
				continue;
			if (r.getRegionX() != x || r.getRegionZ() != z)
				continue;
			return r;
		}
		cacheIndex++;
		cacheIndex = cacheIndex % CACHE_SIZE;
		CoreAnvilRegionFile region = new CoreAnvilRegionFile(world, x, z, worldDir);
		cache[cacheIndex] = region;
		return region;
	}

}
