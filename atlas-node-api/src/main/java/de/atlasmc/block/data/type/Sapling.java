package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Sapling extends BlockData {
	
	int getMaxStage();
	
	int getStage();
	
	void setStage(int stage);

}
