package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Chest;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreChest extends CoreAbstractContainerTile<Inventory> implements Chest {

	public CoreChest(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.CHEST, getCustomName(), this);
	}

}
