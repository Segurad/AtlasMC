package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.inventory.ItemStack;

public interface Item extends Entity {
	
	public static final NBTCodec<Item>
	NBT_HANDLER = NBTCodec
					.builder(Item.class)
					.include(Entity.NBT_CODEC)
					.shortField("Age", Item::getLifeTime, Item::setLifeTime, (short) 6000)
					.shortField("Health", Item::getHealth, Item::setHealth, (short) 5)
					.codec("Item", Item::getItem, Item::setItem, ItemStack.NBT_HANDLER)
					.codec("Owner", Item::getOwner, Item::setOwner, NBTCodecs.UUID_CODEC)
					.shortField("PickupDelay", Item::getPickupDelay, Item::setPickupDelay, (short) 0)
					.codec("Thrower", Item::getThrower, Item::setThrower, NBTCodecs.UUID_CODEC)
					.build();
	
	ItemStack getItem();
	
	void setItem(ItemStack item);
	
	/**
	 * Returns the time in ticks until this item is pickupable or -1 if generally not.<br>
	 * Will be remain at 0 if it is pickupable
	 * @return ticks or -1
	 */
	int getPickupDelay();
	
	void setPickupDelay(int delay);
	
	/**
	 * Returns the {@link UUID} of the Entity that is capable of picking up this item or null if any Entity can
	 * @return uuid or null
	 */
	UUID getOwner();
	
	void setOwner(UUID uuid);
	
	UUID getThrower();
	
	void setThrower(UUID uuid);
	
	void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks until this item despawns or -1 if it does not despawn
	 * @return ticks or -1
	 */
	int getLifeTime();
	
	int getHealth();
	
	void setHealth(int health);
	
	@Override
	default NBTCodec<? extends Item> getNBTCodec() {
		return NBT_HANDLER;
	}

}
