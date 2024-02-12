package de.atlasmc.block.tile;

import de.atlasmc.Material;
import de.atlasmc.SimpleLocation;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public interface TileEntity extends NBTHolder, Cloneable {

	TileEntity clone();
	
	Material getType();
	
	void setType(Material material);
	
	/**
	 * Returns the <u><b>relative</b></u> Location of this Tile in the Chunk
	 * @return location
	 */
	SimpleLocation getLocation();
	
	int getX();
	
	int getY();
	
	int getZ();
	
	Chunk getChunk();
	
	World getWorld();
	
	@InternalAPI
	void setLocation(Chunk chunk, int x, int y, int z);

	int getID();
	
}
