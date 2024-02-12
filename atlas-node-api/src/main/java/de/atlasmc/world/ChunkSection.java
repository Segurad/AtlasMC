package de.atlasmc.world;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.NibbleArray;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.palette.Palette;

public interface ChunkSection {
	
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
	@UnsafeAPI
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
	 */
	public void setBlockData(BlockData data, int x, int y, int z);
	
	Biome getBiome(int x, int y, int z);
	
	void setBiome(Biome biome, int x, int y, int z);
	
	NibbleArray getBlockLight();
	
	boolean hasBlockLight();
	
	NibbleArray getSkyLight();
	
	boolean hasSkyLight();
	
	@UnsafeAPI
	Palette<BlockData> getBlockData();
	
	@UnsafeAPI
	Palette<Biome> getBiomes();
	
}
