package de.atlasmc.block;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.EnumBiome;
import de.atlasmc.world.World;

public interface Block {

	public Material getType();
	public void setBlockData(BlockData data);
	public void setType(Material material);
	public BlockData getBlockData();
	public int getY();
	public int getX();
	public int getZ();
	public EnumBiome getBiome();
	public World getWorld();
	
}
