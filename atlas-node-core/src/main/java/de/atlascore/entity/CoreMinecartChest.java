package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartChest;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreMinecartChest extends CoreAbstractMinecartContainer implements MinecartChest {

	public CoreMinecartChest(EntityType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return InventoryType.CHEST.create(this);
	}

}
