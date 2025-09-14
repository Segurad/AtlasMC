package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.ShulkerBox;
import de.atlasmc.node.inventory.ContainerFactory;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreShulkerBox extends CoreAbstractContainerTile<Inventory> implements ShulkerBox {

	public CoreShulkerBox(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.SHULKER_BOX, this);
	}

}
