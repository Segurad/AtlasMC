package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface Scaffolding extends Waterlogged {
	
	public int getDistance();
	public int getMaxDistance();
	public boolean isBottom();
	public void setBottom(boolean bottom);
	public void setDistance(int distance);

}
