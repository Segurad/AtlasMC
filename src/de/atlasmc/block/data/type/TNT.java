package de.atlasmc.block.data.type;

import de.atlasmc.block.data.BlockData;

public interface TNT extends BlockData {
	
	public boolean isUnstable();
	public void setUnstable(boolean stable);

}
