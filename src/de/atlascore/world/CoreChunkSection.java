package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.ChunkSection;

/**
 * Default implementation of {@link ChunkSection}
 */
public class CoreChunkSection implements ChunkSection {
	
	private final List<CorePaletteEntry> palette;
	
	/**
	 * 0x00F - X pos<br>
	 * 0x0F0 - Z pos<br>
	 * 0xF00 - Y pos<br>
	 */
	private final short[] indizes; // ordered by y > z > x (as hex 0xY00 + 0xZ0 + 0xX)

	public CoreChunkSection() {
		palette = new ArrayList<CorePaletteEntry>();
		CorePaletteEntry entry = new CorePaletteEntry(Material.AIR.createBlockData());
		entry.count = 4096;
		palette.add(entry);
		indizes = new short[4096];
	}
	
	@Override
	public short[] getIndizes() {
		return Arrays.copyOf(indizes, indizes.length);
	}

	@Override
	public short[] getIndizes(short[] buffer, int offset) {
		for (short s : indizes) {
			buffer[offset++] = s;
		}
		return buffer;
	}

	@Override
	public short getIndex(int x, int y, int z) {
		return indizes[getIndizesIndex(x, y, z)];
	}

	@Override
	public void setIndex(short value, int x, int y, int z) {
		palette.get(value).count++; // increment new values palette entry
		final int index = getIndizesIndex(x, y, z);
		palette.get(indizes[index]).count--; // decrement old values palette entry
		indizes[index] = value; // replace old value
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
		return getPalette(new ArrayList<BlockData>(palette.size()));
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
		return palette.size();
	}

	@Override
	public int getBitsPerBlock() {
		int size = getPaletteSize();
		int bits = 4, mask = 0x0F;
		while (mask < size) {
			mask = (mask << 1) + 1;
			bits++;
		}
		return bits;
	}

	@Override
	public long[] getCompactMappings() {
		final int bits = getBitsPerBlock();
		long[] data = new long[(4096*bits)>>6];
		int index = 0;
		int restBits = 64;
		long values = 0;
		for (short s : indizes) {
			if (restBits < bits) {
				data[index++] = values;
				values = 0;
				restBits = 64;
			}
			values <<= bits; // shift to create space for next index 
			values |= s; // add next index
			restBits -= bits;
		}
		data[index] = values;
		return data;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		BlockData data = palette.get(getIndex(x, y, z)).data;
		return data.clone();
	}
	
	@Override
	public BlockData getBlockDataUnsafe(int x, int y, int z) {
		return palette.get(getIndex(x, y, z)).data;
	}

	@Override
	public short setBlockData(BlockData data, int x, int y, int z) {
		short paletteIndex = addPaletteEntry(data);
		setIndex(paletteIndex, x, y, z);
		return paletteIndex;
	}

	@Override
	public short getPaletteIndex(BlockData data) {
		if (data == null) throw new IllegalArgumentException("Palette can not contain null!");
		short index = -1;
		for (CorePaletteEntry entry : palette) {
			index++;
			if (entry.data.equals(data)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		CorePaletteEntry entry = palette.get(getIndex(x, y, z));
		BlockData data = entry.data;
		return data.getMaterial();
	}

	@Override
	public short addPaletteEntry(BlockData data) {
		final short index = getPaletteIndex(data); // check if present
		if (index != -1) return index; 
		final int size = palette.size();
		for (short i = 0; i < size; i++) { // check for unused entry for replacement
			CorePaletteEntry entry = palette.get(i);
			if (entry.count > 0) continue;
			entry.data = data.clone();
			return i;
		}
		// add new entry
		palette.add(new CorePaletteEntry(data.clone()));
		return (short) (palette.size()-1);
	}

}
