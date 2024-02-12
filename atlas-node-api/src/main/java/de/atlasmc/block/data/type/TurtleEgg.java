package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Hatchable;

public interface TurtleEgg extends Hatchable {
	
	int getEggs();
	
	int getMaxEggs();
	
	int getMinEggs();
	
	void setEggs(int eggs);

}
