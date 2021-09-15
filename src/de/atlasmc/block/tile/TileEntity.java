package de.atlasmc.block.tile;

import de.atlasmc.Material;
import de.atlasmc.util.nbt.NBTHolder;

public interface TileEntity extends NBTHolder, Cloneable {

	public TileEntity clone();
	
	public Material getType();
	
	public int getX();
	
	public int getY();
	
	public int getZ();

}
