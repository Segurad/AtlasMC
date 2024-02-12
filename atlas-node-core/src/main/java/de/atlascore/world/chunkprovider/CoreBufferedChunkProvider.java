package de.atlascore.world.chunkprovider;

import java.util.Collection;

import de.atlasmc.util.map.Long2ObjectMap;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

/**
 * ChunkProvider implementation that stores Chunks in a Buffer for dynamic world size
 */
public class CoreBufferedChunkProvider extends CoreAbstractChunkProvider {
	
	private final Long2ObjectMap<Chunk> chunks;
	
	public CoreBufferedChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
		super(world, generator, loader);
		this.chunks = new Long2ObjectMap<>();
	}

	@Override
	public Collection<Chunk> getChunks() {
		return chunks.values();
	}

	@Override
	protected Chunk getCachedChunk(long pos, int x, int z) {
		return chunks.get(pos);
	}

	@Override
	protected void addCachedChunk(long pos, Chunk chunk) {
		chunks.put(pos, chunk);
	}

}
