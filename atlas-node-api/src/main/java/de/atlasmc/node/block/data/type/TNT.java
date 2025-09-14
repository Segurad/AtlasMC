package de.atlasmc.node.block.data.type;

import de.atlasmc.node.block.data.BlockData;

public interface TNT extends BlockData {
	
	public boolean isUnstable();
	public void setUnstable(boolean stable);

}
