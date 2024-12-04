package de.atlasmc.block.blockpredicate;

import de.atlasmc.Material;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.block.tile.TileEntity;

public interface BlockPredicate {
	
	boolean matches(TileEntity tile);
	
	boolean matches(BlockData data);
	
	boolean matches(Material material);

}
