package de.atlasmc.node.world;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.util.palette.Palette;
import de.atlasmc.util.NibbleArray;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.UnsafeAPI;

public interface ChunkSection {
	
	/**
	 * Returns weather or not this section is empty
	 * @return true if only air is present
	 */
	boolean isEmpty();
	
	/**
	 * 
	 * @return the number of non air blocks
	 */
	int getBlockCount();
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return a copy of the BlockData 
	 */
	@NotNull
	BlockData getBlockData(int x, int y, int z);
	
	/**
	 * Returns the BlockData at the position <b>NOT</b> a copy
	 * @param x
	 * @param y
	 * @param z
	 * @return BlockData
	 */
	@NotNull
	@UnsafeAPI
	BlockData getBlockDataUnsafe(int x, int y, int z);

	@NotNull
	BlockType getBlockType(int x, int y, int z);
	
	void setBlockData(BlockData data, int x, int y, int z);
	
	@NotNull
	Biome getBiome(int x, int y, int z);
	
	void setBiome(Biome biome, int x, int y, int z);
	
	/**
	 * Returns the Block light of this section.
	 * If not initialized a empty array will be initialized.
	 * Can be checked for initialization using {@link #hasBlockLight()}
	 * @return array
	 */
	@NotNull
	NibbleArray getBlockLight();
	
	/**
	 * Sets the Block light of this section. May be cleared by setting null.
	 * The given array will be referenced by this section from now on.
	 * @param light to set
	 */
	void setBlockLigth(NibbleArray light);
	
	boolean hasBlockLight();
	
	/**
	 * Sets the Sky light of this section. May be cleared by setting null.
	 * The given array will be referenced by this section from now on.
	 * @param light to set
	 */
	void setSkyLight(NibbleArray light);
	
	/**
	 * Returns the Sky light of this section.
	 * If not initialized a empty array will be initialized.
	 * Can be checked for initialization using {@link #hasSkyLight()}
	 * @return array
	 */
	@NotNull
	NibbleArray getSkyLight();
	
	boolean hasSkyLight();
	
	@NotNull
	@UnsafeAPI
	Palette<BlockData> getBlockData();
	
	@NotNull
	@UnsafeAPI
	Palette<Biome> getBiomes();
	
}
