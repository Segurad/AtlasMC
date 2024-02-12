package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;

public interface Piston extends Directional {
	
	public boolean isExtended();
	public void setExtended(boolean extended);

}
