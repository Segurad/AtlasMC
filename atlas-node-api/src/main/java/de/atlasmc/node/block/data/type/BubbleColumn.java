package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface BubbleColumn extends BlockData {
	
	boolean isDrag();
	
	void setDrag(boolean drag);

}
