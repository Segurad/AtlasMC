package de.atlascore.world;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public class CoreChunkProvider implements Tickable {
	
	private Chunk[] chunkBuffer;
	private long[] chunkPosBuffer;
	private int used, size;
	private final World world;
	
	public CoreChunkProvider(World world) {
		this.world = world;
	}
	
	/**
	 * 
	 * @param x chunk coordinate
	 * @param z chunk coordinate
	 * @return
	 */
	public Chunk getChunk(int x, int z) {
		// X | Y
		final long pos = x << 32 + z;
		for (int i = 0; i < size; i++) {
			if (chunkPosBuffer[i] != pos) continue;
			return chunkBuffer[i];
		}
		// TODO chunk not present
		return null;
	}

	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntities(entities);
		}
		return entities;
	}

	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entities = new ArrayList<T>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClass(entities, clazz);
		}
		return null;
	}

	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x >> 4, z >> 4);
		if (c == null) return 0;
		return c.getHighestBlockYAt(x & 0xF, z & 0xF);
	}

	public void tick() {
		// TODO tick
	}

	public Block getBlock(int x, int y, int z) {
		return new CoreBlockAccess(new Location(world, x, y, z), getChunk(x >> 4, z >> 4));
	}

	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

}
