package de.atlasmc.block.data;

import java.util.List;

import de.atlasmc.Material;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.nbt.NBTHolder;

public interface BlockData extends Cloneable, NBTHolder {

	BlockData clone();
	
	Material getMaterial();
	
	int getStateID();

	List<BlockDataProperty<?>> getProperties();
	
}
