package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartChest;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;

public class CoreMinecartChest extends CoreAbstractMinecartContainer implements MinecartChest {

	public CoreMinecartChest(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected Inventory createInventory() {
		return InventoryType.CHEST.create(this);
	}

}
