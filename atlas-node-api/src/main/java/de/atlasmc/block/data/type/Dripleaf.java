package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Waterlogged;

public interface Dripleaf extends Directional, Waterlogged {
	
	Dripleaf clone();

}
