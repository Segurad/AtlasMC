package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Piston extends Directional {
	
	boolean isExtended();
	
	void setExtended(boolean extended);

}
