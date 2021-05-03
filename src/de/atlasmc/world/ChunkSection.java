package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public interface ChunkSection {
	
	/**
	 * 
	 * @return a copy of the mappings
	 */
	public short[] getIndizes();
	public default short[] getMappings(short[] buffer) {
		return getIndizes(buffer, 0);
	}
	public short[] getIndizes(short[] buffer, int offset);
	public short getIndex(int x, int y, int z);
	public void setIndex(short value, int x, int y, int z);
	/**
	 * 
	 * @return a copy of the palette
	 */
	public List<BlockData> getPalette();
	
	public List<BlockData> getPalette(List<BlockData> palette);
	public boolean isEmpty();
	/**
	 * 
	 * @return the number of non air blocks
	 */
	public int getBlockCount();
	public int getPaletteSize();
	/**
	 * 
	 * @return number of bits needed to represent a index
	 * minimum is 4 bit per index
	 */
	public int getBitsPerBlock();
	
	/**
	 * 
	 * @return compacts the mappings
	 */
	public long[] getCompactMappings();
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return a copy of the BlockData 
	 */
	public BlockData getBlockData(int x, int y, int z);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public Material getBlockType(int x, int y, int z);
	
	/**
	 * 
	 * @param data
	 * @param x
	 * @param y
	 * @param z
	 * @return the palette index of this data
	 */
	public int setBlockData(BlockData data, int x, int y, int z);
	
	/**
	 * 
	 * @param data
	 * @return the palette index of this data or -1 if not present
	 */
	public int getPaletteIndex(BlockData data);

}
