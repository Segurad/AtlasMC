package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.World;

/**
 * ChunkProvider implementation that stores Chunks in a Buffer for dynamic world size
 */
public class CoreBufferedChunkProvider implements ChunkProvider {
	
	private Chunk[] chunkBuffer; // stores all currently loaded chunks the index equals the index in the position buffer
	private long[] chunkPosBuffer; // stores the position of a chunk as long 32 most significant bits X 32 least significant bits Z
	private int used; // number of chunks currently buffered
	private final World world;
	private final ChunkGenerator generator;
	private final ChunkLoader loader;
	
	public CoreBufferedChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
		this.world = world;
		this.generator = generator;
		this.loader = loader;
	}
	
	@Override
	public Chunk getChunk(int x, int z) {
		// X | Z
		final long pos = x << 32 | z;
		for (int i = 0; i < used; i++) {
			if (chunkPosBuffer[i] != pos) continue;
			return chunkBuffer[i];
		}
		Chunk chunk = loader.loadChunk(world, x, z);
		if (chunk == null)
			chunk = generator.generate(world, x, z);
		if (chunk == null)
			return null;
		addChunk(chunk, pos);
		return chunk;
	}

	@Override
	public List<Entity> getEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntities(entities);
		}
		return entities;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entities = new ArrayList<T>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClass(entities, clazz);
		}
		return null;
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<Entity>();
		for (Chunk chunk : chunkBuffer) {
			if (chunk == null) continue;
			chunk.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x >> 4, z >> 4);
		if (c == null) return 0;
		return c.getHighestBlockYAt(x & 0xF, z & 0xF);
	}

	@Override
	public void tick() {
		// TODO tick
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		return new CoreBlockAccess(new Location(world, x, y, z), getChunk(x >> 4, z >> 4));
	}

	@Override
	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		return getChunk(x >> 4, z >> 4).getBlockDataAt(x, y, z);
	}
	
	private void addChunk(Chunk chunk, long pos) {
		ensureBufferSize();
		chunkPosBuffer[used] = pos;
		chunkBuffer[used] = chunk;
		used++;
	}
	
	private void removeChunk(Chunk chunk) {
		for (int i = 0; i < used; i++) {
			if (chunkBuffer[i] != chunk)
				continue;
			removeChunk(i);
			break;
		}
	}
	
	private void removeChunk(int index) {
		used--;
		if (used > 0) {
			chunkPosBuffer[index] = chunkPosBuffer[used];
			chunkBuffer[index] = chunkBuffer[used];
		} else chunkBuffer[0] = null;
	}
	
	private void ensureBufferSize() {
		int size = chunkPosBuffer.length;
		if (used < size)
			return;
		size *= size >> 1;
		chunkPosBuffer = Arrays.copyOf(chunkPosBuffer, size);
		chunkBuffer = Arrays.copyOf(chunkBuffer, size);
	}

}
