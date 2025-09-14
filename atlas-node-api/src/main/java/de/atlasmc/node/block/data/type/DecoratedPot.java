package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Directional;
import de.atlasmc.node.block.data.Waterlogged;

public interface DecoratedPot extends Directional, Waterlogged {
	
	boolean isCracked();
	
	void setCracked(boolean cracked);
	
	DecoratedPot clone();

}
