package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Distanced;
import de.atlasmc.block.data.Waterlogged;

public interface Scaffolding extends Waterlogged, Distanced {
	
	boolean isBottom();
	
	void setBottom(boolean bottom);

}
