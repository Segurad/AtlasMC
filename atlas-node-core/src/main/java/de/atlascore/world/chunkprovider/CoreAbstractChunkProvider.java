package de.atlascore.world.chunkprovider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.atlascore.block.CoreBlockAccess;
import de.atlasmc.Location;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.ChunkProvider;
import de.atlasmc.world.World;

public abstract class CoreAbstractChunkProvider implements ChunkProvider {

	protected final World world;
	protected final ChunkGenerator generator;
	protected final ChunkLoader loader;
	
	public CoreAbstractChunkProvider(World world, ChunkGenerator generator, ChunkLoader loader) {
		if (world == null)
			throw new IllegalArgumentException("World can not be null!");
		if (generator == null)
			throw new IllegalArgumentException("Generator can not be null!");
		if (loader == null)
			throw new IllegalArgumentException("Load can not be null!");
		this.world = world;
		this.generator = generator;
		this.loader = loader;
	}
	
	@Override
	public Chunk getChunk(int x, int z, boolean load) {
		// X | Z
		final long pos = x << 32 | z;
		Chunk chunk = getCachedChunk(pos, x, z);
		if (chunk != null)
			return chunk;
		if (!load)
			return null;
		Future<Chunk> chunkFuture = loader.loadChunk(x, z);
		try {
			chunk = chunkFuture.get();
		} catch (Exception e) {
			world.getServer().getLogger().error("Error while loading chunk!", e);
		}
		if (chunk == null) {
			chunkFuture = generator.generate(x, z);
			try {
				chunk = chunkFuture.get();
			} catch (Exception e) {
				world.getServer().getLogger().error("Error while generating chunk!", e);
			}
		}
		if (chunk == null)
			return null;
		addCachedChunk(pos, chunk);
		return chunk;
	}
	
	@Override
	public Future<Chunk> getChunkLater(int x, int z) {
		// X | Z
		final long pos = x << 32 | z;
		Chunk chunk = getCachedChunk(pos, x, z);
		if (chunk != null)
			return new CompleteFuture<>(chunk);
		final CompletableFuture<Chunk> future = new CompletableFuture<>(); 
		loader.loadChunk(x, z).setListener((futureChunk) -> {
			final Throwable cause = futureChunk.cause();
			if (cause != null) {
				world.getServer().runTask(() -> {
					world.getServer().getLogger().error("Error while loading chunk", cause);
					future.finish(null, cause);
				});
				return;
			}
			final Chunk c = (Chunk) futureChunk.getNow();
			if (c != null) {
				world.getServer().runTask(() -> {
					addCachedChunk(pos, chunk);
					future.finish(c);
				});
				return;
			}
			generateChunk(future, pos, x, z);
		});
		return future;
	}
	
	private void generateChunk(CompletableFuture<Chunk> future, long pos, int x, int z) {
		generator.generate(x, z).setListener((futureChunk) -> {
			final Throwable cause = futureChunk.cause();
			if (cause != null) {
				world.getServer().runTask(() -> {
					world.getServer().getLogger().error("Error while generating chunk", cause);
					future.finish(null, cause);
				});
				return;
			}
			final Chunk c = (Chunk) futureChunk.getNow();
			world.getServer().runTask(() -> {
				if (c != null)
					addCachedChunk(pos, c);
				future.finish(c);
			});
		});
	}
	
	protected abstract Chunk getCachedChunk(long pos, int x, int z);
	
	protected abstract void addCachedChunk(long pos, Chunk chunk);

	@Override
	public Collection<Entity> getEntities() {
		List<Entity> entities = new ArrayList<>();
		for (Chunk chunk : getChunks()) {
			if (chunk == null) 
				continue;
			chunk.getEntities(entities);
		}
		return entities;
	}

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		List<T> entities = new ArrayList<>();
		for (Chunk chunk : getChunks()) {
			if (chunk == null) 
				continue;
			chunk.getEntitiesByClass(entities, clazz);
		}
		return null;
	}

	@Override
	public Collection<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		List<Entity> entities = new ArrayList<>();
		for (Chunk chunk : getChunks()) {
			if (chunk == null) 
				continue;
			chunk.getEntitiesByClasses(entities, classes);
		}
		return entities;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		Chunk c = getChunk(x >> 4, z >> 4, false);
		if (c == null) 
			return 0;
		return c.getHighestBlockYAt(x & 0xF, z & 0xF);
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		Chunk chunk = getChunk(x >> 4, z >> 4, false);
		if (chunk == null)
			return null;
		return new CoreBlockAccess(new Location(world, x, y, z), chunk);
	}

	@Override
	public Entity getEntity(int entityID) {
		for (Chunk chunk : getChunks()) {
			Entity entity = chunk.getEntity(entityID);
			if (entity != null)
				return entity;
		}
		return null;
	}
	
	@Override
	public void tick() {
		for (Chunk chunk : getChunks()) {
			chunk.tick();
		}
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		Chunk chunk = getChunk(x >> 4, z >> 4, false);
		if (chunk == null)
			return null;
		return chunk.getBlockDataAt(x, y, z);
	}

}
