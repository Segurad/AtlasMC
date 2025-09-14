package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Barrel;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreBarrel extends CoreAbstractContainerTile<Inventory> implements Barrel {

	public CoreBarrel(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.BARREL, this);
	}

}
