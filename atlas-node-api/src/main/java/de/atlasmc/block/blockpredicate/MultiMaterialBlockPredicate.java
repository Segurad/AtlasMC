package de.atlasmc.block.blockpredicate;

import java.util.Collection;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public class MultiMaterialBlockPredicate implements BlockPredicate {
	
	private final Collection<Material> materials;
	
	public MultiMaterialBlockPredicate(Collection<Material> materials) {
		this.materials = materials;
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
