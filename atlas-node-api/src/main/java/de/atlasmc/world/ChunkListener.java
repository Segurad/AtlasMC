package de.atlasmc.world;

public interface ChunkListener {
	
	public void updateBlock(Chunk chunk, int data, int x, int y, int z);

	/**
	 * Called when this listener is removed from the {@link Chunk}
	 * @param chunk the {@link Chunk} this listener is removed from
	 */
	public void removed(Chunk chunk);
	
	/**
	 * Called when this listener is added to the a {@link Chunk}
	 * @param chunk the chunk this listener is added to
	 */
	public void added(Chunk chunk);
	
}
