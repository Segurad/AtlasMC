package de.atlasmc.inventory.meta;

import de.atlasmc.block.data.BlockData;

public interface BlockDataMeta extends ItemMeta {
	
	public BlockDataMeta clone();
	public BlockData getBlockData();
	public boolean hasBlockData();
	public void setBlockData(BlockData data);

}
