package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface Cake extends BlockData {
	
	int getBites();
	
	int getMaxBites();
	
	void setBites(int bites);

}
