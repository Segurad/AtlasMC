package de.atlasmc.factory;

import de.atlasmc.Material;
import de.atlasmc.block.tile.TileEntity;
import de.atlasmc.registry.RegistryHolder;

/**
 * Factory for creating {@link TileEntity}
 */
@RegistryHolder(key="atlas:factory/tile_entity_factory")
public interface TileEntityFactory {
	
	boolean isValidTile(TileEntity tile);
	
	TileEntity createTile(Material material);

	int getTileID();
	
}
