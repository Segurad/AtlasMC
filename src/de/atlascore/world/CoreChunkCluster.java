package de.atlascore.world;

import java.util.List;

import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;
import de.atlasmc.world.Chunk;

public class CoreChunkCluster implements Tickable {
	
	private final Chunk[] chunks;
	private final int x, z;
	
	public CoreChunkCluster(int x, int z) {
		chunks = new Chunk[1024];
		this.x = x;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

	public Chunk getChunk(int x, int z) {
		return chunks[z*32+x];
	}

	public List<Entity> getEntities(List<Entity> entites) {
		for (Chunk c : chunks) {
			if (c == null) continue;
			c.getEntites(entites);
		}
		return entites;
	}

	public <T extends Entity> List<T> getEntitiesByClass(List<T> entites, Class<T> clazz) {
		for (Chunk c : chunks) {
			if (c == null || !c.isLoaded()) continue;
			c.getEntitesByClass(entites, clazz);
		}
		return entites;
	}

	public void tick() {
		for (Chunk c : chunks) {
			if (c == null) continue;
			c.tick();
		}
	}

	public List<Entity> getEntitiesByClasses(List<Entity> entities, Class<? extends Entity>[] classes) {
		for (Chunk c : chunks) {
			if (c == null || !c.isLoaded()) continue;
			c.getEntitesByClasses(entities, classes);
		}
		return entities;
	}

}
