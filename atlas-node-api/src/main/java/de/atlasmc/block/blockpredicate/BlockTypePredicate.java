package de.atlasmc.block.blockpredicate;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.dataset.DataSet;

public class BlockTypePredicate implements BlockPredicate {

	private DataSet<BlockType> materials;
	
	public BlockTypePredicate(DataSet<BlockType> materials) {
		setMaterials(materials);
	}
	
	public void setMaterials(DataSet<BlockType> materials) {
		if (materials == null)
			throw new IllegalArgumentException("Materials can not be null!");
		this.materials = materials;
	}
	
	public DataSet<BlockType> getMaterials() {
		return materials;
	}

	@Override
	public boolean matches(TileEntity tile) {
		return materials.contains(tile.getType());
	}

	@Override
	public boolean matches(BlockData data) {
		return materials.contains(data.getType());
	}

	@Override
	public boolean matches(BlockType material) {
		return materials.contains(material);
	}
	
}
