package de.atlasmc.core.node.world;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.util.palette.AdaptivePalette;
import de.atlasmc.node.util.palette.GlobalValueProvider;
import de.atlasmc.node.util.palette.Palette;
import de.atlasmc.node.util.palette.PaletteEntry;
import de.atlasmc.node.world.Biome;
import de.atlasmc.node.world.ChunkSection;
import de.atlasmc.util.NibbleArray;

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
		final BlockType air = BlockType.AIR.get();
		for (PaletteEntry<BlockData> entry : blockData.getEntries()) {
			if (air == entry.getEntry().getType())
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
	public BlockType getBlockType(int x, int y, int z) {
		BlockData data = blockData.getEntry(getBlockIndex(x, y, z));
		return data != null ? data.getType() : null;
	}

	@Override
	public NibbleArray getBlockLight() {
		if (blocklight == null)
			blocklight = new NibbleArray(2048);
		return blocklight;
	}
	
	@Override
	public void setBlockLigth(NibbleArray light) {
		this.blocklight = light;
	}

	@Override
	public NibbleArray getSkyLight() {
		if (skylight == null)
			skylight = new NibbleArray(2048);
		return skylight;
	}
	
	@Override
	public void setSkyLight(NibbleArray light) {
		this.skylight = light;
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
