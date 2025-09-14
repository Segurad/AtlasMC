package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Openable;
import de.atlasmc.node.block.data.Powerable;

public interface Gate extends Directional, Openable, Powerable {
	
	boolean isInWall();
	
	void setInWall(boolean inWall);

}
