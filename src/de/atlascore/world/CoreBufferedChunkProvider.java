package de.atlascore.world;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.entity.Entity;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.World;

/**
 * ChunkProvider implementation that stores Chunks in a Buffer for dynamic world size
 */
public class CoreBufferedChunkProvider implements ChunkProvider {
	
	private Chunk[] chunkBuffer; // stores all currently loaded chunks the index equals the index in the position buffer
	private long[] chunkPosBuffer; // stores the position of a chunk as long 32 most significant bits X 32 least significant bits Z
	private int used; // number of chunks currently buffered
	private int last; // position of the last chunk in the buffer
	private final World world;
	
	public CoreBufferedChunkProvider(World world) {
		this.world = world;
	}
	
	@Override
	public Chunk getChunk(int x, int z) {
		// X | Z
		final long pos = x << 32 | z;
		for (int i = 0; i < last; i++) {
			if (chunkPosBuffer[i] != pos) continue;
			return chunkBuffer[i];
		}
		// TODO chunk not present
		return null;
	}

	@Override
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntities(entities);
		}
		return entities;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entities = new ArrayList<T>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClass(entities, clazz);
		}
		return null;
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x >> 4, z >> 4);
		if (c == null) return 0;
		return c.getHighestBlockYAt(x & 0xF, z & 0xF);
	}

	@Override
	public void tick() {
		// TODO tick
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return new CoreBlockAccess(new Location(world, x, y, z), getChunk(x >> 4, z >> 4));
	}

	@Override
	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

}
