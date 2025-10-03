package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;

public interface DriedGhast extends Directional, Waterlogged {

	int getHydration();
	
	void setHydration(int hydration);
	
}
