package de.atlasmc.world;

import java.util.List;

import de.atlasmc.block.data.BlockData;

public interface ChunkSection {
	
	/**
	 * 
	 * @return a copy of the mappings
	 */
	public short[] getMappings();
	public default short[] getMappings(short[] buffer) {
		return getMappings(buffer, 0);
	}
	public short[] getMappings(short[] buffer, int offset);
	public void setMappings(short[] mappings);
	public short getValue(int x, int y, int z);
	public void setValue(short value, int x, int y, int z);
	/**
	 * 
	 * @return a copy of the palette
	 */
	public List<BlockData> getPalette();
	public boolean isEmpty();
	public int getBlockCount();
	public int getPaletteSize();
	public int getBitsPerBlock();
	
	/**
	 * 
	 * @return compacts the mappings
	 */
	public long[] getCompactMappings();

}
