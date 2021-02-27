package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface Lantern extends Waterlogged {
	
	public boolean isHanging();
	public void setHanding(boolean hanging);

}
