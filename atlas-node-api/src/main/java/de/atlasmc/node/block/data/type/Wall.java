package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.HightConnectable;
import de.atlasmc.node.block.data.Waterlogged;

public interface Wall extends Waterlogged, HightConnectable {
	
	boolean isUp();
	
	void setUp(boolean up);

}
