package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.WanderingTrader;
import de.atlasmc.node.inventory.ItemStack;

public class CoreWanderingTrader extends CoreMerchant implements WanderingTrader {
	
	private List<ItemStack> pocket;
	private int despawnDelay;
	
	public CoreWanderingTrader(EntityType type) {
		super(type);
	}

	@Override
	public void setDespawnDelay(int delay) {
		this.despawnDelay = delay;
	}

	@Override
	public int getDespawnDelay() {
		return despawnDelay;
	}

	@Override
	public List<ItemStack> getPocketItems() {
		if (pocket == null)
			pocket = new ArrayList<>();
		return pocket;
	}

	@Override
	public boolean hasPocketItems() {
		return pocket != null && !pocket.isEmpty();
	}
	
}
