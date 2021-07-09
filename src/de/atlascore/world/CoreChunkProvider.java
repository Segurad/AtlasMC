package de.atlascore.world;

import java.util.ArrayList;
import java.util.List;

import de.atlascore.block.CoreBlock;
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
	
	public Chunk getChunk(int x, int z) {
		// X | Y
		final long pos = (x >> 4) << 32 + z >> 4;
		for (int i = 0; i < size; i++) {
			if (chunkPosBuffer[i] != pos) continue;
			return chunkBuffer[i];
		}
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
		Chunk c = getChunk(x, z);
		if (c == null) return 0;
		return c.getHighestBlockYAt(x, z);
	}

	public void tick() {
		// TODO tick
	}

	public Block getBlock(int x, int y, int z) {
		return new CoreBlock(new Location(world, x, y, z), getChunk(x, z));
	}

}
