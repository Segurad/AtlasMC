package de.atlasmc.block.blockpredicate;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.util.dataset.DataSet;

public class MaterialBlockPredicate implements BlockPredicate {

	private DataSet<Material> materials;
	
	public MaterialBlockPredicate(DataSet<Material> materials) {
		setMaterials(materials);
	}
	
	public void setMaterials(DataSet<Material> materials) {
		if (materials == null)
			throw new IllegalArgumentException("Materials can not be null!");
		this.materials = materials;
	}
	
	public DataSet<Material> getMaterials() {
		return materials;
	}

	@Override
	public boolean matches(TileEntity tile) {
		return materials.contains(tile.getType());
	}

	@Override
	public boolean matches(BlockData data) {
		return materials.contains(data.getMaterial());
	}

	@Override
	public boolean matches(Material material) {
		return materials.contains(material);
	}
	
}
