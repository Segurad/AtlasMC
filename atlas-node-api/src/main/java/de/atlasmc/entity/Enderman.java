package de.atlasmc.entity;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;

public interface Enderman extends Monster {

	BlockType getCarriedBlockType();
	
	BlockData getCarriedBlock();
	
	void setCarriedBlock(BlockData data);
	
	void setCarriedBlockType(BlockType type);
	
	/**
	 * Sets the carried block as changed for the next update
	 */
	void setCarriedBlockChanged();
	
	boolean hasCarriedBlock();
	
	void setScreaming(boolean screaming);
	
	boolean isScreaming();
	
	void setStaring(boolean staring);
	
	boolean isStaring();
	
}
