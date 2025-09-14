package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Waterlogged;

public interface SeaPickle extends Waterlogged {
	
	public int getMaxPickles();
	public int getMinPickles();
	public int getPickles();
	public void setPickles(int pickles);

}
