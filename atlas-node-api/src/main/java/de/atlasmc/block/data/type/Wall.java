package de.atlasmc.block.data.type;

import de.atlasmc.block.data.HightConnectable;
import de.atlasmc.block.data.Waterlogged;

public interface Wall extends Waterlogged, HightConnectable {
	
	boolean isUp();
	
	void setUp(boolean up);

}
