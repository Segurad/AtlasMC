package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;

public interface BlockDisplay extends Display {
	
	BlockData getBlockData();
	
	void setBlockData(BlockData data);

	void setBlockDataType(BlockType type);
	
	BlockType getBlockDataType();

}
