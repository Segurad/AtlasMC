package de.atlasmc.entity;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;

public interface BlockDisplay extends Display {
	
	BlockData getBlockData();
	
	void setBlockData(BlockData data);

	void setBlockDataType(Material mat);
	
	Material getBlockDataType();

}
