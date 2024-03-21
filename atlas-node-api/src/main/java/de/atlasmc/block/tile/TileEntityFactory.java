package de.atlasmc.block.tile;

import de.atlasmc.Material;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.Factory;

/**
 * Factory for creating {@link TileEntity}
 */
@RegistryHolder(key="atlas:factory/tile_entity_factory")
public interface TileEntityFactory extends Factory {
	
	boolean isValidTile(TileEntity tile);
	
	TileEntity createTile(Material material);

	int getTileID();
	
}
