package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SkeletonHorse;
import de.atlasmc.factory.ContainerFactory;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.world.World;

public class CoreSkeletonHorse extends CoreAbstractHorse implements SkeletonHorse {

	
	//protected static final String TODO unnecessary
	//NBT_SKELETON_TRAP = "SkeletonTrap",
	//NBT_SKELETON_TRAP_TIME = "SkeletonTrapTime";
	
	public CoreSkeletonHorse(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected AbstractHorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}

}
