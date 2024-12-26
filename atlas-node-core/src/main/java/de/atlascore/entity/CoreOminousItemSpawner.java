package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.OminousItemSpawner;
import de.atlasmc.inventory.ItemStack;

public class CoreOminousItemSpawner extends CoreEntity implements OminousItemSpawner {

	protected ItemStack item;
	protected long spawnTicks;
	
	public CoreOminousItemSpawner(EntityType type, UUID uuid) {
		super(type, uuid);
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
