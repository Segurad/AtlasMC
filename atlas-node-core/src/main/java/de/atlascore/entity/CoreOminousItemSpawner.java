package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.OminousItemSpawner;
import de.atlasmc.inventory.ItemStack;

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
