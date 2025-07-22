package de.atlasmc.entity;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface OminousItemSpawner extends Entity {
	
	public static final NBTSerializationHandler<OminousItemSpawner>
	NBT_HANDLER = NBTSerializationHandler
					.builder(OminousItemSpawner.class)
					.include(Entity.NBT_HANDLER)
					.typeCompoundField("item", OminousItemSpawner::getItem, OminousItemSpawner::setItem, ItemStack.NBT_HANDLER)
					.longField("spawn_item_after_ticks", OminousItemSpawner::getSpawnTicks, OminousItemSpawner::setSpawnTicks)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	long getSpawnTicks();
	
	void setSpawnTicks(long ticks);
	
	@Override
	default NBTSerializationHandler<? extends OminousItemSpawner> getNBTHandler() {
		return NBT_HANDLER;
	}

}
