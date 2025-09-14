package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface Cake extends BlockData {
	
	int getBites();
	
	int getMaxBites();
	
	void setBites(int bites);

}
