package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ZombieHorse;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.AbstractHorseInventory;

public class CoreZombieHorse extends CoreAbstractHorse implements ZombieHorse {

	public CoreZombieHorse(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected AbstractHorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}

}
