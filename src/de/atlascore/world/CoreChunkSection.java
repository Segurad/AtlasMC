package de.atlascore.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.Chunk;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public short setValue(short value, int x, int y, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BlockData> getPalette() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getBlockCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPaletteSize() {
		// TODO Auto-generated method stub
		return 0;
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
		long[] data = new long[(16*16*16*getBitsPerBlock())/64];
		// TODO
		return data;
	}
	
	

}
