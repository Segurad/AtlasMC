package de.atlascore.inventory.meta;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.inventory.meta.BlockDataMeta;

public class CoreBlockDataMeta extends CoreItemMeta implements BlockDataMeta {

	private BlockData data;
	private Material material;
	public CoreBlockDataMeta(Material material) {
		this(material, null);
	}
	
	public CoreBlockDataMeta(Material material, BlockData data) {
		super(material);
		this.material = material;
		this.data = data;
	}

	@Override
	public BlockData getBlockData() {
		if (data == null) data = material.createBlockData();
		return data;
	}

	@Override
	public boolean hasBlockData() {
		return data != null;
	}

	@Override
	public void setBlockData(BlockData data) {
		this.data = data;
	}

}
