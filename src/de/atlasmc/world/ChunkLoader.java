package de.atlasmc.world;

public interface ChunkLoader {
	
	public Chunk loadChunk(int x, int z);
	
	public void saveChunk(Chunk chunk, int x, int z);

}
