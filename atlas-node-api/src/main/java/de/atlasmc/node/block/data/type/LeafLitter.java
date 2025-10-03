package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface LeafLitter extends Directional {

	int getSegmentAmount();
	
	void setSegmentAmount(int amount);
	
}
