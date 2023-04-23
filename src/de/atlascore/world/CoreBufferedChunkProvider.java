package de.atlascore.world;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.map.Long2ObjectHashMap;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.World;

/**
 * ChunkProvider implementation that stores Chunks in a Buffer for dynamic world size
 */
public class CoreBufferedChunkProvider implements ChunkProvider {
	
	private final Long2ObjectHashMap<Chunk> chunks;
	private final World world;
	private final ChunkGenerator generator;
	private final ChunkLoader loader;
	
	public CoreBufferedChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
		this.world = world;
		this.generator = generator;
		this.loader = loader;
		this.chunks = new Long2ObjectHashMap<>();
	}
	
	@Override
	public Chunk getChunk(int x, int z, boolean load) {
		// X | Z
		final long pos = x << 32 | z;
		Chunk chunk = chunks.get(pos);
		if (chunk != null)
			return chunk;
		if (!load)
			return null;
		chunk = loader.loadChunk(world, x, z);
		if (chunk == null)
			chunk = generator.generate(world, x, z);
		if (chunk == null)
			return null;
		chunks.put(pos, chunk);
		return chunk;
	}

	@Override
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<>();
		for (Chunk chunk : chunks) {
			if (chunk == null) continue;
			chunk.getEntities(entities);
		}
		return entities;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entities = new ArrayList<>();
		for (Chunk chunk : chunks) {
			if (chunk == null) continue;
			chunk.getEntitiesByClass(entities, clazz);
		}
		return null;
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<>();
		for (Chunk chunk : chunks) {
			if (chunk == null) continue;
			chunk.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x >> 4, z >> 4, false);
		if (c == null) 
			return 0;
		return c.getHighestBlockYAt(x & 0xF, z & 0xF);
	}

	@Override
	public void tick() {
		// TODO tick
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		Chunk chunk = getChunk(x >> 4, z >> 4, false);
		if (chunk == null)
			return null;
		return new CoreBlockAccess(new Location(world, x, y, z), chunk);
	}

	@Override
	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		Chunk chunk = getChunk(x >> 4, z >> 4, false);
		if (chunk == null)
			return null;
		return chunk.getBlockDataAt(x, y, z);
	}

}
