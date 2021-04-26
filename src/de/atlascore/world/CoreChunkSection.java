package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.ChunkSection;

public class CoreChunkSection implements ChunkSection {
	
	private final List<BlockData> palette;
	private final short[] mappings;

	public CoreChunkSection() {
		palette = new ArrayList<BlockData>();
		mappings = new short[16*16*16];
	}
	
	@Override
	public short[] getMappings() {
		return Arrays.copyOf(mappings, mappings.length);
	}

	@Override
	public short[] getMappings(short[] buffer, int offset) {
		for (short s : mappings) {
			buffer[offset++] = s;
		}
		return buffer;
	}

	@Override
	public void setMappings(short[] mappings) {
		
	}

	@Override
	public short getValue(int x, int y, int z) {
		return mappings[z*16*y*16+x];
	}

	@Override
	public void setValue(short value, int x, int y, int z) {
		mappings[z*16*y*16+x] = value;
	}

	@Override
	public List<BlockData> getPalette() {
		return palette;
	}

	@Override
	public boolean isEmpty() {
		return palette.isEmpty();
	}

	@Override
	public int getBlockCount() {
		return 0;
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
		long[] data = new long[(16*16*16*bits)/64];
		int index = 0;
		int restBits = 64;
		long values = 0;
		for (short s : mappings) {
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

}
