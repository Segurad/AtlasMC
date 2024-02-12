package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Ageable;
import de.atlasmc.block.data.Waterlogged;

public interface MangrovePropagule extends Ageable, Sapling, Waterlogged {
	
	boolean isHanging();
	
	void setHanging(boolean hanging);
	
	MangrovePropagule clone();

}
