package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface Sapling extends BlockData {
	
	int getMaxStage();
	
	int getStage();
	
	void setStage(int stage);

}
