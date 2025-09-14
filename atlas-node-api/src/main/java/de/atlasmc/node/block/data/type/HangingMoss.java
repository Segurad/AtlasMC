package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface HangingMoss extends BlockData {

	boolean isTip();
	
	void setTip(boolean tip);
	
}
