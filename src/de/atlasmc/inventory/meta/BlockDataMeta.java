package de.atlasmc.inventory.meta;

import de.atlasmc.block.data.BlockData;

public interface BlockDataMeta extends ItemMeta {
	
	public BlockData getBlockData();
	public boolean hasBlockData();
	public void setBlockData(BlockData data);

}
