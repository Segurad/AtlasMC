package de.atlasmc.block.blockpredicate;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public class MaterialBlockPredicate implements BlockPredicate {

	private final Material material;
	
	public MaterialBlockPredicate(Material material) {
		this.material = material;
	}

	@Override
	public boolean matches(TileEntity tile) {
		return tile.getType() == material;
	}

	@Override
	public boolean matches(BlockData data) {
		return data.getMaterial() == material;
	}

	@Override
	public boolean matches(Material material) {
		return material == this.material;
	}
	
}
