package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface SculkCatalyst extends BlockData {
	
	boolean isBloom();
	
	void setBloom(boolean bloom);
	
	SculkCatalyst clone();

}
