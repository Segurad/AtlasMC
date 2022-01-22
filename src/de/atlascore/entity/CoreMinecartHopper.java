package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.MinecartHopper;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.world.World;

public class CoreMinecartHopper extends CoreAbstractMinecartContainer implements MinecartHopper {

	public CoreMinecartHopper(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected Inventory createInventory() {
		return ContainerFactory.GENERIC_INV_FACTORY.create(InventoryType.HOPPER, this);
	}

}
