package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Openable;
import de.atlasmc.block.data.Powerable;

public interface Gate extends Directional, Openable, Powerable {
	
	public boolean isInWall();
	public void setInWall(boolean inWall);

}
