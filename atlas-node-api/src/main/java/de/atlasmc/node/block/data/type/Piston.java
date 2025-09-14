package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;

public interface Piston extends Directional {
	
	boolean isExtended();
	
	void setExtended(boolean extended);

}
