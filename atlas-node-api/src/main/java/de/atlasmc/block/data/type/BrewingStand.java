package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface BrewingStand extends BlockData {
	
	boolean hasBottle(int slot);
	
	void setBottle(int slot, boolean has);

}
