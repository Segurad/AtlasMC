package de.atlasmc.world;

public enum ChunkStatus {
	
	/**
	 * The Chunk chunk is unknown (mostly due to not cached)
	 */
	UNKNOWN,
	/**
	 * The Chunk is currently in generation process
	 */
	GENERATING,
	/**
	 * The Chunk is currently in loading process
	 */
	LOADING,
	/**
	 * The Chunk is loaded 
	 */
	LOADED,
	/**
	 * The Chunk is unloaded and removed from cache
	 */
	UNLOADED;

}
