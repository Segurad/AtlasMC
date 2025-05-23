package de.atlascore.block.tile;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Barrel;
import de.atlasmc.inventory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreBarrel extends CoreAbstractContainerTile<Inventory> implements Barrel {

	public CoreBarrel(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.BARREL, this);
	}

}
