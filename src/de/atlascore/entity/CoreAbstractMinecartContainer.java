package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.AbstractMinecartContainer;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.world.World;

public abstract class CoreAbstractMinecartContainer extends CoreAbstractMinecart implements AbstractMinecartContainer {

	private Inventory inv;
	
	public CoreAbstractMinecartContainer(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = createInventory();
		return inv;
	}

	protected abstract Inventory createInventory();

}
