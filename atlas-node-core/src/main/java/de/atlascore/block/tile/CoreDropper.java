package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Dropper;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreDropper extends CoreAbstractContainerTile<Inventory> implements Dropper {

	public CoreDropper(Material type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.DROPPER, this);
	}

}
