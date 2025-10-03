package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface Flower extends Directional {
	
	int getFlowerAmount();
	
	void setFlowerAmount(int amount);
	
	int getMaxFlowerAmount();

	Flower clone();
	
}
