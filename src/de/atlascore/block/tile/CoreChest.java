package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Chest;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.world.Chunk;

public class CoreChest extends CoreAbstractContainerTile<Inventory> implements Chest {

	public CoreChest(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.CHEST, getCustomName(), this);
	}

}
