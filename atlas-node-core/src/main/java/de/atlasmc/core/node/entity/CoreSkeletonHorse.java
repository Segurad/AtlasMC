package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.SkeletonHorse;
import de.atlasmc.node.inventory.AbstractHorseInventory;
import de.atlasmc.node.inventory.ContainerFactory;

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
