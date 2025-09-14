package de.atlasmc.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.registry.RegistryHolder;
import de.atlasmc.util.factory.Factory;

/**
 * Factory for creating {@link TileEntity}
 */
@RegistryHolder(key="atlas:factory/tile_entity_factory")
public interface TileEntityFactory extends Factory {
	
	boolean isValidTile(TileEntity tile);
	
	TileEntity createTile(BlockType type);

	int getTileID();
	
}
