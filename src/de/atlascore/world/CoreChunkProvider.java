package de.atlascore.world;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.world.Chunk;

public class CoreChunkProvider {
	
	private final List<CoreChunkCluster> clusters;
	
	public CoreChunkProvider() {
		clusters = new ArrayList<CoreChunkCluster>();
	}
	
	public Chunk getChunk(int x, int z) {
		int clusterX = x >> 9;
		int clusterZ = z >> 9;
		for (CoreChunkCluster cluster : clusters) {
			if (cluster.getX() != x || cluster.getZ() != z) continue;
			clusterX = x - (clusterX << 9);
			clusterZ = z - (clusterZ << 9);
			return cluster.getChunk(x, z);
		}
		return null;
	}

}
