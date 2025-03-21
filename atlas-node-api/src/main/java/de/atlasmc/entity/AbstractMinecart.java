package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;

public interface AbstractMinecart extends Vehicle {
	
	BlockData getCustomBlock();
	
	boolean hasCustomBlock();
	
	void setCustomBlock(BlockData data);
	
	void setCustomBlockType(BlockType type);
	
	int getCustomBlockY();
	
	void setCustomBlockY(int y);
	
	boolean getShowCustomBlock();

	void setShowCustomBlock(boolean show);
	
}
