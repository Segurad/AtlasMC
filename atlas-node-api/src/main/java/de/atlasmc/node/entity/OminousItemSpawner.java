package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface OminousItemSpawner extends Entity {
	
	@NotNull
	public static final NBTCodec<OminousItemSpawner>
	NBT_CODEC = NBTCodec
					.builder(OminousItemSpawner.class)
					.include(Entity.NBT_CODEC)
					.codec("item", OminousItemSpawner::getItem, OminousItemSpawner::setItem, ItemStack.NBT_CODEC)
					.longField("spawn_item_after_ticks", OminousItemSpawner::getSpawnTicks, OminousItemSpawner::setSpawnTicks)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	long getSpawnTicks();
	
	void setSpawnTicks(long ticks);
	
	@Override
	default NBTCodec<? extends OminousItemSpawner> getNBTCodec() {
		return NBT_CODEC;
	}

}
