package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;

/**
 * Factory for creating {@link TileEntity}
 */
public interface TileEntityFactory {
	
	public boolean isValidTile(TileEntity tile);
	
	public TileEntity createTile(Material material);
	
}
