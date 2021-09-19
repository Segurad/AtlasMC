package de.atlasmc.block.tile;

import de.atlasmc.Material;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Chunk;

public interface TileEntity extends NBTHolder, Cloneable {

	public TileEntity clone();
	
	public Material getType();
	
	public void setType(Material material);
	
	public Chunk getChunk();
	
	public int getX();
	
	public int getY();
	
	public int getZ();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public void setZ(int z);

}
