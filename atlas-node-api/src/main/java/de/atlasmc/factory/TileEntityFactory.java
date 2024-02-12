package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;

/**
 * Factory for creating {@link TileEntity}
 */
public interface TileEntityFactory {
	
	boolean isValidTile(TileEntity tile);
	
	TileEntity createTile(Material material);

	int getTileID();
	
}
