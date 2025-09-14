package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.TraderLlama;

public class CoreTraderLlama extends CoreLlama implements TraderLlama {

	private int despawnDelay;
	
	public CoreTraderLlama(EntityType type) {
		super(type);
	}

	@Override
	public int getDespawnDelay() {
		return despawnDelay;
	}

	@Override
	public void setDespawnDelay(int delay) {
		this.despawnDelay = delay;
	}

}
