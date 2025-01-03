package de.atlasmc.block;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.world.Biome;
import de.atlasmc.world.ChunkSection;
import de.atlasmc.world.World;

public interface Block {

	Material getType();
	
	void setBlockData(BlockData data);
	
	void setType(Material material);
	
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
	Location getLocation();
	
	Location getLocation(Location loc);
	
}
