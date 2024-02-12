package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface SculkShrieker extends Waterlogged {
	
	boolean canSummon();
	
	void setCanSummon(boolean canSummon);
	
	boolean isShrieking();
	
	void setShrieking(boolean shrieking);
	
	SculkShrieker clone();

}
