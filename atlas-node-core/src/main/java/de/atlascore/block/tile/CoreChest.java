package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Chest;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreChest extends CoreAbstractContainerTile<Inventory> implements Chest {

	public CoreChest(Material type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.CHEST, getCustomName(), this);
	}

}
