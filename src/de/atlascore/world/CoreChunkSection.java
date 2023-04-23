package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.MathUtil;
import de.atlasmc.util.NibbleArray;
import de.atlasmc.util.VariableValueArray;
import de.atlasmc.world.ChunkSection;

/**
 * Default implementation of {@link ChunkSection}
 */
public class CoreChunkSection implements ChunkSection {
	
	private CorePaletteEntry[] palette;
	private int lastPaletteEntry = 0;
	
	/**
	 * 0x00F - X pos<br>
	 * 0x0F0 - Z pos<br>
	 * 0xF00 - Y pos<br>
	 */
	private final VariableValueArray indizes; // ordered by y > z > x (as hex 0xY00 + 0xZ0 + 0xX)
	
	private NibbleArray blocklight;
	private NibbleArray skylight;

	public CoreChunkSection() {
		palette = new CorePaletteEntry[16];
		addPaletteEntry(Material.AIR.createBlockData());
		indizes = new VariableValueArray(4096, 4);
	}
	
	@Override
	public VariableValueArray getIndizes() {
		return indizes.clone();
	}
	
	@Override
	public VariableValueArray getIndizes(VariableValueArray buff) {
		buff.copy(indizes);
		return buff;
	}
	
	@Override
	public VariableValueArray getIndizesUnsafe() {
		return indizes;
	}

	@Override
	public int getIndex(int x, int y, int z) {
		return indizes.get(getIndizesIndex(x, y, z));
	}

	@Override
	public void setIndex(int value, int x, int y, int z) {
		palette[value].count++; // increment new values palette entry
		final int index = getIndizesIndex(x, y, z);
		palette[indizes.replace(index, value)].count--; // decrement old values palette entry and set set new index
	}

	/**
	 * This method compact the coordinates as a index<br>
	 * @implNote does not check if the coordinates belong to this section
	 * @param x
	 * @param y
	 * @param z
	 * @return the index for the indizes
	 */
	protected int getIndizesIndex(int x, int y, int z) {
		return (z & 0xF) << 4 | (y & 0xF) << 8 | (x & 0xF);
	}

	@Override
	public List<BlockData> getPalette() {
		return getPalette(new ArrayList<>(lastPaletteEntry));
	}
	
	@Override
	public List<BlockData> getPalette(List<BlockData> palette) {
		for (CorePaletteEntry entry : this.palette) {
			palette.add(entry.data.clone());
		}
		return palette;
	}

	@Override
	public boolean isEmpty() {
		return getBlockCount() == 0;
	}

	@Override
	public int getBlockCount() {
		int count = 0;
		for (CorePaletteEntry entry : palette) {
			if (entry.getMaterial().isAir())
				continue;
			count += entry.count;
		}
		return count;
	}

	@Override
	public int getPaletteSize() {
		return lastPaletteEntry;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		BlockData data = palette[getIndex(x, y, z)].data;
		return data.clone();
	}
	
	@Override
	public BlockData getBlockDataUnsafe(int x, int y, int z) {
		return palette[getIndex(x, y, z)].data;
	}

	@Override
	public int setBlockData(BlockData data, int x, int y, int z) {
		int paletteIndex = addOrReplacePalette(data, x , y, z, true);
		setIndex(paletteIndex, x, y, z);
		return paletteIndex;
	}

	@Override
	public int getPaletteIndex(BlockData data) {
		if (data == null) 
			throw new IllegalArgumentException("Palette can not contain null!");
		short index = -1;
		for (CorePaletteEntry entry : palette) {
			if (entry == null)
				return -1;
			index++;
			if (entry.data.equals(data)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		CorePaletteEntry entry = palette[getIndex(x, y, z)];
		BlockData data = entry.data;
		return data.getMaterial();
	}

	@Override
	public int addPaletteEntry(BlockData data) {
		return addOrReplacePalette(data, 0, 0, 0, false);
	}
	
	/**
	 * Adds the BlockData to the palette if it not exist or returns the index of the existing element.<br>
	 * @param data
	 * @param x
	 * @param y
	 * @param z
	 * @param checkCoordinates if true it will be checked if the palette entry a the location has a 
	 * count of 1 and if so it will be replaced to keep a smaller palette
	 * @return
	 */
	protected int addOrReplacePalette(BlockData data, int x, int y, int z, boolean checkCoordinates) {
		final int index = getPaletteIndex(data); // check if present
		if (index != -1) 
			return index; 
		if (checkCoordinates) {
			int paletteIndex = getIndex(x, y, z);
			if (palette[paletteIndex].count == 1) { // if the entry at the index has a count of 1 to replace it a
				palette[paletteIndex].data = data.clone();
				palette[paletteIndex].count = 1;
				return paletteIndex;
			}
		}
		for (short i = 0; i < lastPaletteEntry; i++) { // check for unused entry for replacement
			CorePaletteEntry entry = palette[i];
			if (entry.count > 0) 
				continue;
			entry.data = data.clone();
			return i;
		}
		// add new entry
		if (palette.length == lastPaletteEntry) { // grow palette
			palette = Arrays.copyOf(palette, palette.length << 1);
			indizes.resize(MathUtil.getBitsPerBlock(palette.length));
		}
		palette[lastPaletteEntry] = new CorePaletteEntry(data.clone());
		return lastPaletteEntry++;
	}

	@Override
	public void recalcPalette() {
		for (CorePaletteEntry entry : palette)
			entry.count = 0;
		for (int i = 0; i < 0xFFF; i++)
			palette[indizes.get(i)].count++;
	}

	@Override
	public int getPaletteEntryBlockCount(int index) {
		if (index < 0 || index >= palette.length)
			return -1;
		return palette[index].count;
	}

	@Override
	public int getPaletteEntryBlockCount(BlockData data) {
		for (CorePaletteEntry entry : palette)
			if (entry.data.equals(data))
				return entry.count;
		return -1;
	}

	@Override
	public boolean setPaletteEntryBlockCount(int index, int count) {
		if (count < 0)
			throw new IllegalArgumentException("Count can not be lower than 0: " + count);
		if (index < 0 || index >= palette.length)
			return false;
		palette[index].count = (short) count;
		return true;
	}

	@Override
	public boolean setPaletteEntryBlockCount(BlockData data, int count) {
		if (count < 0)
			throw new IllegalArgumentException("Count can not be lower than 0: " + count);
		for (CorePaletteEntry entry : palette)
			if (entry.data.equals(data)) {
				entry.count = (short) count;
				return true;
			}
		return false;
	}

	@Override
	public int setPaletteEntry(int index, BlockData data, boolean addIfOutOfBounds) {
		if (index < 0 || index >= palette.length)
			if (addIfOutOfBounds)
				return addPaletteEntry(data);
			else
				return -1;
		palette[index].data = data.clone();
		return index;
	}

	@Override
	public BlockData getPaletteEntry(int index) {
		return palette[index].data;
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

}
