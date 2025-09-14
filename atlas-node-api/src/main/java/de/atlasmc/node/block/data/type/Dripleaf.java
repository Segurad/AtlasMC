package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;

public interface Dripleaf extends Directional, Waterlogged {
	
	Dripleaf clone();

}
