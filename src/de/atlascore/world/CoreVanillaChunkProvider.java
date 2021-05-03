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

public class CoreVanillaChunkProvider implements Tickable {
	
	private final List<CoreChunkCluster> clusters;
	private final World world;
	
	public CoreVanillaChunkProvider(World world) {
		clusters = new ArrayList<CoreChunkCluster>();
		this.world = world;
	}
	
	public Chunk getChunk(int x, int z) {
		int clusterX = x >> 9;
		int clusterZ = z >> 9;
		for (CoreChunkCluster cluster : clusters) {
			if (cluster.getX() != clusterX || cluster.getZ() != clusterZ) continue;
			return cluster.getChunk(x >> 4, z >> 4);
		}
		return null;
	}

	public List<Entity> getEntities() {
		List<Entity> entites = new ArrayList<Entity>();
		for (CoreChunkCluster cluster : clusters) {
			cluster.getEntities(entites);
		}
		return entites;
	}

	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entites = new ArrayList<T>();
		for (CoreChunkCluster cluster : clusters) {
			cluster.getEntitiesByClass(entites, clazz);
		}
		return null;
	}

	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<Entity>();
		for (CoreChunkCluster cluster : clusters) {
			cluster.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x, z);
		if (c == null) return 0;
		return c.getHighestBlockYAt(x, z);
	}

	public void tick() {
		for (CoreChunkCluster c : clusters) {
			c.tick();
		}
	}

	public Block getBlock(int x, int y, int z) {
		return new CoreBlock(new Location(world, x, y, z), getChunk(x, z));
	}

}
