package de.atlascore.world;

import java.util.Arrays;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.NumberConversion;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.Future;
import de.atlasmc.util.configuration.InvalidConfigurationException;
import de.atlasmc.util.configuration.ConfigurationSection;
import de.atlasmc.util.palette.Palette;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkFactory;
import de.atlasmc.world.ChunkGenerator;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.World;

import static de.atlascore.world.CoreChunkSection.getBlockIndex;

public class CoreFlatworldChunkGenerator implements ChunkGenerator {

	private final World world;
	private final ChunkFactory chunkFactory;
	private final BlockData[] blocks;
	private final int[] layers;
	private final int startheight;
	
	public CoreFlatworldChunkGenerator(World world, ChunkFactory chunkFactory, List<BlockData> palette, int[] stack, int startheight) {
		if (world == null)
			throw new IllegalArgumentException("World can not be null!");
		if (chunkFactory == null)
			throw new IllegalArgumentException("Chunk factory can not be null!");
		this.world = world;
		this.chunkFactory = chunkFactory;
		if (palette == null || stack == null) {
			this.blocks = null;
			this.layers = null;
			this.startheight = 0;
			return;
		}
		this.blocks = new BlockData[palette.size()];
		for (int i = 0; i < palette.size(); i++) {
			this.blocks[i] = palette.get(i).clone();
		}
		this.layers = Arrays.copyOf(stack, stack.length);
		this.startheight = startheight;
	}
	
	public CoreFlatworldChunkGenerator(World world, ChunkFactory chunkFactory, ConfigurationSection config) {
		if (world == null)
			throw new IllegalArgumentException("World can not be null!");
		if (chunkFactory == null)
			throw new IllegalArgumentException("Chunk factory can not be null!");
		if (config == null)
			throw new IllegalArgumentException("Config can not be null!");
		this.world = world;
		this.chunkFactory = chunkFactory;
		this.startheight = config.getInt("start-height");
		List<String> layers = config.getStringList("layers");
		if (layers == null)
			throw new InvalidConfigurationException("\"layers\" is not defined!");
		this.layers = new int[layers.size()];
		Arrays.fill(this.layers, -1);
		this.blocks = new BlockData[layers.size()];
		int index = 0;
		for (String raw : layers) {
			String[] parts = raw.split(" : ");
			Material mat = Material.getByName(parts[0]);
			if (mat == null)
				throw new InvalidConfigurationException("Unknown Material (" + parts[0] + ") at layer: " + index, config);
			int size = NumberConversion.toInt(parts[1], Integer.MIN_VALUE);
			if (size == Integer.MIN_VALUE) {
				throw new InvalidConfigurationException("Unable to parse layer with at layer: " + index, config);
			}
			if (size < 1) {
				throw new InvalidConfigurationException("Layer with must be greater than 0 (" + size + ") at layer: " + index, config);
			}
			if (!mat.isBlock()) {
				throw new InvalidConfigurationException("Material must be a block (" + mat.getNamespacedKeyRaw() + ") at layer:" + index);
			}
			BlockData data = mat.createBlockData();
			this.blocks[index] = data;
			this.layers[index] = size;
			index++;
		}
	}
	
	@Override
	public Future<Chunk> generate(int x, int z) {
		final CompletableFuture<Chunk> future = new CompletableFuture<>();
		ChunkWorker.queueTask((logger) -> {
			Chunk chunk = chunkFactory.createChunk(world, x, z);
			if (this.blocks != null) { // Fill chunk with Blocks				
				for (int y = startheight, i = 0; i < layers.length; i++) {
					ChunkSection section = chunk.getSection(y);
					Palette<BlockData> palette = section.getBlockData();
					int sectionPaletteIndex = palette.getEntryValue(this.blocks[i]);
					// add missing entry
					if (sectionPaletteIndex == -1) {
						sectionPaletteIndex = palette.setEntry(this.blocks[i], getBlockIndex(0, y, 0));
					}
					for (int restLayer = layers[i]; restLayer > 0; restLayer--, y++) {
						// fill layer of section
						for (int secZ = 0; secZ < 16; secZ++) {
							for (int secX = 0; secX < 16; secX++) {
								palette.setRawEntry(sectionPaletteIndex, getBlockIndex(secX, y, secZ));
							}
						}
					}
				}
			}
			future.finish(chunk);
		});
		return future;
	}

}
