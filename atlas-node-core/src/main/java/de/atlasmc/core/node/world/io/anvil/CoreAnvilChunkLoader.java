package de.atlasmc.core.node.world.io.anvil;

import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.node.world.Chunk;
import de.atlasmc.node.world.ChunkFactory;
import de.atlasmc.node.world.ChunkLoader;
import de.atlasmc.node.world.World;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.ConfigurationSection;

/**
 * Core implementation of {@link ChunkLoader} for the anvil chunk format
 */
public class CoreAnvilChunkLoader implements ChunkLoader {
	
	private final World world;
	private final File worldDir;
	private final ChunkFactory chunkFactory;
	private final boolean disableSave;
	
	private final Set<WeakReference<CoreAnvilRegionFile>> files;
	private final ReferenceQueue<Object> queue;
	
	public CoreAnvilChunkLoader(World world, File worldDir, ChunkFactory factory, ConfigurationSection config) {
		if (world == null) {
			throw new IllegalArgumentException("World can not be null!");
		}
		if (worldDir.exists()) {
			if (!worldDir.isDirectory())
				throw new IllegalArgumentException("Given world directory is not a directory: " + worldDir.getAbsolutePath());
			if (!worldDir.canRead())
				throw new IllegalArgumentException("Unable to read from world directory!");
		}
		if (factory == null)
			throw new IllegalArgumentException("ChunkFactory can not be null!");
		if (config != null) {
			this.disableSave = config.getBoolean("disable-save", false);
		} else {
			this.disableSave = false;
		}
		this.world = world;
		this.worldDir = worldDir;
		this.chunkFactory = factory;
		files = ConcurrentHashMap.newKeySet();
		queue = new ReferenceQueue<>();
	}

	@Override
	public Future<Chunk> loadChunk(int x, int z) {
		CoreAnvilRegionFile regionFile = getRegionFile(x, z);
		return regionFile.loadChunk(world, chunkFactory, null, x, z);
	}

	@Override
	public void saveChunk(Chunk chunk) {
		if (disableSave)
			return;
	}

	@Override
	public Future<Collection<Chunk>> loadRegion(int x, int z) {
		CoreAnvilRegionFile regionFile = getRegionFile(x, z);
		return regionFile.loadChunks(world, chunkFactory);
	}
	
	/**
	 * Returns the {@link CoreAnvilRegionFile} matching the given position
	 * @param x position of the region 
	 * @param z position of the region
	 * @return the {@link CoreAnvilRegionFile}
	 */
	private synchronized CoreAnvilRegionFile getRegionFile(int x, int z) {
		cleanup();
		for (WeakReference<CoreAnvilRegionFile> ref : files) {
			if (ref.refersTo(null))
				continue;
			CoreAnvilRegionFile file = ref.get();
			if (file == null)
				continue;
			return file;
		}
		CoreAnvilRegionFile region = new CoreAnvilRegionFile(x, z, worldDir);
		files.add(new WeakReference<>(region, queue));
		return region;
	}
	
	private void cleanup() {
		WeakReference<?> ref = null;
		while ((ref = (WeakReference<?>) queue.poll()) != null)
			files.remove(ref);
	}

}
