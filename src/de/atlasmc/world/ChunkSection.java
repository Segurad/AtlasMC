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
	
	/**
	 * Returns the Palette index at the position<br>
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public short getIndex(int x, int y, int z);
	
	/**
	 * Sets the Palette index at the position<br>
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setIndex(short index, int x, int y, int z);
	
	/**
	 * 
	 * @return a copy of the palette
	 */
	public List<BlockData> getPalette();
	
	/**
	 * Stores copies of all palette entries in the list
	 * @param palette
	 * @return the buffer palette
	 */
	public List<BlockData> getPalette(List<BlockData> palette);
	
	/**
	 * Returns weather or not this section is empty
	 * @return true if only air is present
	 */
	public boolean isEmpty();
	
	/**
	 * 
	 * @return the number of non air blocks
	 */
	public int getBlockCount();
	
	/**
	 * Return the number of elements in the palette
	 * @return
	 */
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
	 * Returns the BlockData at the position <b>NOT</b> a copy
	 * @param x
	 * @param y
	 * @param z
	 * @return BlockData
	 */
	public BlockData getBlockDataUnsafe(int x, int y, int z);
	
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
