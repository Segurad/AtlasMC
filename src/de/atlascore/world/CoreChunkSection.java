package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.ChunkSection;

public class CoreChunkSection implements ChunkSection {
	
	private final List<CorePaletteEntry> palette;
	private final short[] indizes;

	public CoreChunkSection() {
		palette = new ArrayList<CorePaletteEntry>();
		CorePaletteEntry entry = new CorePaletteEntry(Material.AIR.createBlockData());
		entry.setCount(4096);
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
		return indizes[(z&0xF)<<4+(y & 0xF)<<8+x];
	}

	@Override
	public void setIndex(short value, int x, int y, int z) {
		palette.get(value).incrementCount();
		int index = (z&0xF)<<4+(y & 0xF)<<8+x;
		palette.get(indizes[index]).decrementCount();
		indizes[index] = value;
	}

	@Override
	public List<BlockData> getPalette() {
		return getPalette(new ArrayList<BlockData>(palette.size()));
	}

	@Override
	public boolean isEmpty() {
		return getBlockCount() == 0;
	}

	@Override
	public int getBlockCount() {
		int count = 0;
		for (CorePaletteEntry entry : palette) {
			int id = entry.getMaterial().getBlockID();
			if (id == Material.AIR.getBlockID() 
				|| id == Material.VOID_AIR.getBlockID() 
				|| id == Material.CAVE_AIR.getBlockID()) {
				continue;
			}
			count += entry.getCount();
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
			values = (values << bits) | s;
			restBits -= bits;
		}
		data[index] = values;
		return data;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		BlockData data = palette.get(getIndex(x, y, z)).getData();
		return data.clone();
	}

	@Override
	public int setBlockData(BlockData data, int x, int y, int z) {
		int paletteValue = getPaletteIndex(data);
		if (paletteValue == -1) {
			final int size = palette.size();
			for (int i = 0; i < size; i++) {
				CorePaletteEntry entry = palette.get(i);
				if (entry.getCount() > 0) continue;
				paletteValue = i;
				palette.set(i, new CorePaletteEntry(data));
				break;
			}
			if (paletteValue == -1) {
				palette.add(new CorePaletteEntry(data.clone()));
				paletteValue = palette.size()-1;
			}
		}
		setIndex((short) paletteValue, x, y, z);
		return paletteValue;
	}

	@Override
	public int getPaletteIndex(BlockData data) {
		int index = -1;
		for (CorePaletteEntry entry : palette) {
			index++;
			if (entry.getData().equals(data)) {
				return index;
			}
		}
		return -1;
	}

	@Override
	public List<BlockData> getPalette(List<BlockData> palette) {
		for (CorePaletteEntry entry : this.palette) {
			palette.add(entry.getData().clone());
		}
		return palette;
	}

	@Override
	public Material getBlockType(int x, int y, int z) {
		BlockData data = palette.get(getIndex(x, y, z)).getData();
		if (data == null) return null;
		return data.getMaterial();
	}

}
