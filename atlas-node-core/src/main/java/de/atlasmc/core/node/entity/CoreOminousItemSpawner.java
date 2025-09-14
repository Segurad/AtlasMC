package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.OminousItemSpawner;
import de.atlasmc.node.inventory.ItemStack;

public class CoreOminousItemSpawner extends CoreEntity implements OminousItemSpawner {

	protected ItemStack item;
	protected long spawnTicks;
	
	public CoreOminousItemSpawner(EntityType type) {
		super(type);
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

	@Override
	public long getSpawnTicks() {
		return spawnTicks;
	}

	@Override
	public void setSpawnTicks(long ticks) {
		this.spawnTicks = ticks;
	}

}
