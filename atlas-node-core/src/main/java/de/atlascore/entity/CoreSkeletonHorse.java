package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SkeletonHorse;
import de.atlasmc.inventory.AbstractHorseInventory;
import de.atlasmc.inventory.ContainerFactory;

public class CoreSkeletonHorse extends CoreAbstractHorse implements SkeletonHorse {

	private boolean skeletonTrap;
	private int skeletonTrapTime;
	
	public CoreSkeletonHorse(EntityType type) {
		super(type);
	}
	
	@Override
	protected AbstractHorseInventory createInventory() {
		return ContainerFactory.HORSE_INV_FACTORY.create(this);
	}

	@Override
	public boolean isSkeletonTrap() {
		return skeletonTrap;
	}

	@Override
	public void setSkeletonTrap(boolean trap) {
		this.skeletonTrap = trap;
	}

	@Override
	public int getSkeletonTrapTime() {
		return skeletonTrapTime;
	}

	@Override
	public void setSkeletonTrapTime(int time) {
		this.skeletonTrapTime = time;
	}

}
