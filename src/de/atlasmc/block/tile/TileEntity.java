package de.atlasmc.block.tile;

import de.atlasmc.Location;
import de.atlasmc.Material;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public interface TileEntity extends NBTHolder, Cloneable {

	public TileEntity clone();
	
	public Material getType();
	
	public void setType(Material material);
	
	public Chunk getChunk();
	
	/**
	 * Returns the <u><b>relative</b></u> Location of this Tile in the Chunk
	 * @return location
	 */
	public Location getLocation();
	
	public World getWorld();
	
	public int getX();
	
	public int getY();
	
	public int getZ();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setZ(int z);

}
