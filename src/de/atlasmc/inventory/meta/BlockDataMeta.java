package de.atlasmc.inventory.meta;

import de.atlasmc.block.data.BlockData;

public interface BlockDataMeta extends ItemMeta {
	
	public BlockDataMeta clone();
	
	@Override
	public default Class<? extends BlockDataMeta> getInterfaceClass() {
		return BlockDataMeta.class;
	}
	
	public BlockData getBlockData();
	public boolean hasBlockData();
	public void setBlockData(BlockData data);

}
