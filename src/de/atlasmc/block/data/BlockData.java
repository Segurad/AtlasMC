package de.atlasmc.block.data;

import de.atlasmc.Material;
import de.atlasmc.util.nbt.NBTHolder;

public interface BlockData extends Cloneable, NBTHolder {

	public BlockData clone();
	public Material getMaterial();
	public boolean matches(BlockData data);
	public int getStateID();
	
}
