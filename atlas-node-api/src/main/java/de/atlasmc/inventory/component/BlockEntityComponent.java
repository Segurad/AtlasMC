package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.block.tile.TileEntity;

public interface BlockEntityComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:block_entity_data");
	
	BlockEntityComponent clone();
	
	TileEntity getTileEntity();
	
	void setTileEntity(TileEntity tile);

}
