package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Distanced;
import de.atlasmc.node.block.data.Waterlogged;

public interface Leaves extends Waterlogged, Distanced {
	
	boolean isPersistent();
	
	void setPersistent(boolean persistent);

}
