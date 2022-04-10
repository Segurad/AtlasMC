package de.atlasmc.world;

public interface ChunkLoader {

	public Chunk loadChunk(World world, int x, int z);
	
	public void saveChunk(Chunk chunk, int x, int z);

}
