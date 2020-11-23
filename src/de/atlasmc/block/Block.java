package de.atlasmc.block;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.world.Biome;

public interface Block {

	public int getProtocolID();
	public Material getType();
	public void setBlockData(BlockData data);
	public void setType(Material material);
	public BlockData getBlockData();
	public int getY();
	public Biome getBiome();
	
}
