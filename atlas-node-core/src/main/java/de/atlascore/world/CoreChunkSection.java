package de.atlascore.world;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.NibbleArray;
import de.atlasmc.util.palette.AdaptivePalette;
import de.atlasmc.util.palette.GlobalValueProvider;
import de.atlasmc.util.palette.Palette;
import de.atlasmc.util.palette.PaletteEntry;
import de.atlasmc.world.Biome;
import de.atlasmc.world.ChunkSection;

/**
 * Default implementation of {@link ChunkSection}
 */
public class CoreChunkSection implements ChunkSection {
	
	private static final GlobalValueProvider<BlockData> GLOBAL_BLOCK_DATA = (block) -> {
		return block.getStateID();
	};
	private static final GlobalValueProvider<Biome> GLOBAL_BIOMES = (biome) -> {
		return biome.getID();
	};
	
	/**
	 * 0x00F - X pos<br>
	 * 0x0F0 - Z pos<br>
	 * 0xF00 - Y pos<br>
	 */
	private final Palette<BlockData> blockData; // ordered by y > z > x (as hex 0xY00 + 0xZ0 + 0xX)
	private final Palette<Biome> biomes;
	
	private NibbleArray blocklight;
	private NibbleArray skylight;
 
	public CoreChunkSection() {
		blockData = new AdaptivePalette<>(4, 4096, GLOBAL_BLOCK_DATA, 8);
		biomes = new AdaptivePalette<>(1, 64, GLOBAL_BIOMES, 3);
		skylight = new NibbleArray(2048);
		blocklight = new NibbleArray(2048);
	}

	/**
	 * This method compact the coordinates as a index<br>
	 * @implNote does not check if the coordinates belong to this section
	 * @param x
	 * @param y
	 * @param z
	 * @return the index for the indizes
	 */
	public static int getBlockIndex(int x, int y, int z) {
		return ((y & 0xF) << 8) | ((z & 0xF) << 4) | (x & 0xF);
	}
	
	public static int getBiomeIndex(int x, int y, int z) {
		return ((y & 0x1C) << 3) | (z & 0x1C) | ((x & 0x1C) >> 3);
	}

	@Override
	public boolean isEmpty() {
		return getBlockCount() == 0;
	}

	@Override
	public int getBlockCount() {
		int count = 0;
		for (PaletteEntry<BlockData> entry : blockData.getEntries()) {
			if (entry.getEntry().getMaterial().getNamespacedKey().equals(Material.AIR))
				continue;
			count += entry.count();
		}
		return count;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		BlockData data = blockData.getEntry(getBlockIndex(x, y, z));
		return data != null ? data.clone() : null;
	}
	
	@Override
	public BlockData getBlockDataUnsafe(int x, int y, int z) {
		return blockData.getEntry(getBlockIndex(x, y, z));
	}

	@Override
	public void setBlockData(BlockData data, int x, int y, int z) {
		blockData.setEntry(data, getBlockIndex(x, y, z));
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		BlockData data = blockData.getEntry(getBlockIndex(x, y, z));
		return data != null ? data.getMaterial() : null;
	}

	@Override
	public NibbleArray getBlockLight() {
		return blocklight;
	}

	@Override
	public NibbleArray getSkyLight() {
		return skylight;
	}

	@Override
	public boolean hasBlockLight() {
		return blocklight != null;
	}

	@Override
	public boolean hasSkyLight() {
		return skylight != null;
	}

	@Override
	public Palette<BlockData> getBlockData() {
		return blockData;
	}

	@Override
	public Palette<Biome> getBiomes() {
		return biomes;
	}

	@Override
	public Biome getBiome(int x, int y, int z) {
		return biomes.getEntry(getBiomeIndex(x, y, z));
	}

	@Override
	public void setBiome(Biome biome, int x, int y, int z) {
		biomes.setEntry(biome, getBiomeIndex(x, y, z));
	}

}
