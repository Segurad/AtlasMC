package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.Waterlogged;

public interface SculkShrieker extends Waterlogged {
	
	boolean canSummon();
	
	void setCanSummon(boolean canSummon);
	
	boolean isShrieking();
	
	void setShrieking(boolean shrieking);
	
	SculkShrieker clone();

}
