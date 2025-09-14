package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.MinecartChest;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;

public class CoreMinecartChest extends CoreAbstractMinecartContainer implements MinecartChest {

	public CoreMinecartChest(EntityType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return InventoryType.CHEST.create(this);
	}

}
