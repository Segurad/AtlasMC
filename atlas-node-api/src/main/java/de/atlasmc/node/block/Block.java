package de.atlasmc.node.block;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.node.world.Biome;
import de.atlasmc.node.world.ChunkSection;
import de.atlasmc.node.world.World;

public interface Block {

	BlockType getType();
	
	void setBlockData(BlockData data);
	
	void setType(BlockType material);
	
	/**
	 * Returns a copy of the {@link BlockData} at the Blocks position
	 * @return BlockData
	 */
	BlockData getBlockData();
	
	/**
	 * Returns the {@link BlockData} at the Block position<br>
	 * The returned BlockData is <b>NOT</b> a copy and changes will modify the palette of the {@link ChunkSection}
	 * @return BlockData
	 */
	BlockData getBlockDataUnsafe();
	
	TileEntity getTileEntity();
	
	TileEntity getTileEntityUnsafe();
	
	boolean hasTileEntity();
	
	int getY();
	
	int getX();
	
	int getZ();
	
	Biome getBiome();
	
	World getWorld();

	/**
	 * Returns a copy of the Blocks location
	 * @return location
	 */
	WorldLocation getLocation();
	
	WorldLocation getLocation(WorldLocation loc);
	
}
