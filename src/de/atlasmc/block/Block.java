package de.atlasmc.block;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public interface Block {

	public Material getType();
	
	public void setBlockData(BlockData data);
	
	public void setType(Material material);
	
	/**
	 * Returns a copy of the {@link BlockData} at the Blocks position
	 * @return BlockData
	 */
	public BlockData getBlockData();
	
	/**
	 * Returns the {@link BlockData} at the Block position<br>
	 * The returned BlockData is <b>NOT</b> a copy and changes will modify the palette of the {@link ChunkSection}
	 * @return BlockData
	 */
	public BlockData getBlockDataUnsafe();
	
	public int getY();
	
	public int getX();
	
	public int getZ();
	
	public EnumBiome getBiome();
	
	public World getWorld();
	
}
