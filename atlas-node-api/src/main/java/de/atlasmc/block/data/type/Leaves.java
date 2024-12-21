package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Distanced;
import de.atlasmc.block.data.Waterlogged;

public interface Leaves extends Waterlogged, Distanced {
	
	boolean isPersistent();
	
	void setPersistent(boolean persistent);

}
