package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface PinkPetals extends Directional {
	
	int getFlowerAmount();
	
	void setFlowerAmount(int amount);
	
	int getMaxFlowerAmount();

	PinkPetals clone();
	
}
