package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface SculkCatalyst extends BlockData {
	
	boolean isBloom();
	
	void setBloom(boolean bloom);
	
	SculkCatalyst clone();

}
