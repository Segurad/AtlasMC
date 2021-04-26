package de.atlascore.world;

import de.atlasmc.world.Chunk;

public class CoreChunkCluster {
	
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

}
