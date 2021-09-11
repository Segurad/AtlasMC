package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.HumanEntity;
import de.atlasmc.event.inventory.InventoryType;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.PlayerInventory;

public class CoreHumanEntity extends CoreLivingEntity implements HumanEntity {

	private PlayerInventory inv;
	
	public CoreHumanEntity(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
		inv = ContainerFactory.PLAYER_INV_FACTORY.createContainer(InventoryType.PLAYER, this);
	}

	@Override
	public PlayerInventory getInventory() {
		return inv;
	}

}
