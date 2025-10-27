package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface OminousItemSpawner extends Entity {
	
	public static final NBTCodec<OminousItemSpawner>
	NBT_HANDLER = NBTCodec
					.builder(OminousItemSpawner.class)
					.include(Entity.NBT_CODEC)
					.typeCompoundField("item", OminousItemSpawner::getItem, OminousItemSpawner::setItem, ItemStack.NBT_HANDLER)
					.longField("spawn_item_after_ticks", OminousItemSpawner::getSpawnTicks, OminousItemSpawner::setSpawnTicks)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	long getSpawnTicks();
	
	void setSpawnTicks(long ticks);
	
	@Override
	default NBTCodec<? extends OminousItemSpawner> getNBTCodec() {
		return NBT_HANDLER;
	}

}
