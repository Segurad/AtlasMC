package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.ShulkerBox;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreShulkerBox extends CoreAbstractContainerTile<Inventory> implements ShulkerBox {

	public CoreShulkerBox(Material type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.SHULKER_BOX, this);
	}

}
