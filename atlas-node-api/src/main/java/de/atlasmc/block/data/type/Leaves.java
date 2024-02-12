package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface Leaves extends Waterlogged {
	
	int getDistance();
	
	boolean isPersistent();
	
	void setDistance(int distance);
	
	void setPersistent(boolean persistent);

}
