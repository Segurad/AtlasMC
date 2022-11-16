package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.VariableValueArray;

public interface ChunkSection {
	
	/**
	 * Returns the original mappings for this section.<br>
	 * Changes may cause inconsistencies with the palette
	 * @return the original mapping
	 */
	public VariableValueArray getIndizesUnsafe();
	
	/**
	 * Copies all mappings to the provided buffer
	 * @param buff
	 * @return the buffer
	 */
	public VariableValueArray getIndizes(VariableValueArray buff);
	
	/**
	 * 
	 * @return a copy of the mappings
	 */
	public VariableValueArray getIndizes();
	
	/**
	 * Returns the Palette index at the position<br>
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public int getIndex(int x, int y, int z);
	
	/**
	 * Sets the Palette index at the position<br>
	 * @param index
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setIndex(int index, int x, int y, int z);
	
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
	
	/**
	 * Tries to add a copy BlockData to the palette
	 * @param data to add
	 * @return the index of this data is set or the index of present data
	 */
	public int addPaletteEntry(BlockData data);

	
	/**
	 * @see #setPaletteEntry(int, BlockData, boolean)
	 * @implNote setPaletteEntry(index, data, false)
	 * @param index
	 * @param data
	 * @return
	 */
	public default int setPaletteEntry(int index, BlockData data) {
		return setPaletteEntry(index, data, false);
	}
	
	/**
	 * Sets a copy of the data to a palette entry or add the data if index is out of bounds
	 * @param index of the palette
	 * @param data that should be set
	 * @param addIfOutOfBounds whether or not the data should be added when out of the palettes bounds
	 * @return the index the data is set or -1 if not set
	 */
	public int setPaletteEntry(int index, BlockData data, boolean addIfOutOfBounds);
	
	/**
	 * Recalculates the number of blocks present in this chunk per palette entry
	 */
	public void recalcPalette();
	
	/**
	 * Returns the number of blocks associated with a palette entry
	 * @param index of the entry
	 * @return the number of blocks or -1 if no valid index
	 */
	public int getPaletteEntryBlockCount(int index);
	
	/**
	 * Returns the number of blocks associated with the given {@link BlockData}
	 * @param data of the palette
	 * @return the number of blocks or -1 if not present
	 */
	public int getPaletteEntryBlockCount(BlockData data);
	
	/**
	 * Sets the number of blocks associated with a palette entry.
	 * May cause issues if used inappropriate. Should only be used for deep editing of indizes and palette.
	 * The sum of all counts should always be 4096.
	 * Issues can be fixed with {@link #recalcPalette()}
	 * @param index of the palette entry
	 * @param count the new count of the entry
	 * @return true if the index is valid and the count has changed
	 */
	public boolean setPaletteEntryBlockCount(int index, int count);
	
	/**
	 * Sets the number of blocks associated with a palette entry.
	 * May cause issues if used inappropriate. Should only be used for deep editing of indizes and palette.
	 * The sum of all counts should always be 4096.
	 * Issues can be fixed with {@link #recalcPalette()}
	 * @param data of the palette entry
	 * @param count the new count of the entry
	 * @return true if the data is present and has changed
	 */
	public boolean setPaletteEntryBlockCount(BlockData data, int count);
	
}
