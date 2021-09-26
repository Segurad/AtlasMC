package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Barrel;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.world.Chunk;

public class CoreBarrel extends CoreAbstractContainerTile<Inventory> implements Barrel {

	public CoreBarrel(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.BARREL, this);
	}

}
