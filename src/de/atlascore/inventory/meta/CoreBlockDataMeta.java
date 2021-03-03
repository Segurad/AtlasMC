package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.BlockDataMeta;

public class CoreBlockDataMeta extends CoreItemMeta implements BlockDataMeta {

	public CoreBlockDataMeta(Material material) {
		super(material);
	}

	@Override
	public BlockData getBlockData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasBlockData() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setBlockData(BlockData data) {
		// TODO Auto-generated method stub
		
	}

}
