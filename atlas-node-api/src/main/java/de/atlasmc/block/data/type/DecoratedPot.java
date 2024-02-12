package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Directional;
import de.atlasmc.block.data.Waterlogged;

public interface DecoratedPot extends Directional, Waterlogged {
	
	boolean isCracked();
	
	void setCracked(boolean cracked);
	
	DecoratedPot clone();

}
