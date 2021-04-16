package de.atlasmc.block;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Biome;
import de.atlasmc.world.World;

public interface Block extends NBTHolder {

	public int getProtocolID();
	public Material getType();
	public void setBlockData(BlockData data);
	public void setType(Material material);
	public BlockData getBlockData();
	public int getY();
	public Biome getBiome();
	public World getWorld();
	
}
