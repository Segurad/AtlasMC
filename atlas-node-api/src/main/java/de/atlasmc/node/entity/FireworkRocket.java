package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.FireworksComponent;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface FireworkRocket extends Projectile {
	
	public static final NBTCodec<FireworkRocket>
	NBT_HANDLER = NBTCodec
					.builder(FireworkRocket.class)
					.include(Projectile.NBT_HANDLER)
					.typeCompoundField("FireworkItem", FireworkRocket::getFirework, FireworkRocket::setFirework, ItemStack.NBT_HANDLER)
					.intField("Life", FireworkRocket::getLifeTime, FireworkRocket::setLifeTime, 0)
					.intField("LifeTime", FireworkRocket::getMaxLifeTime, FireworkRocket::setMaxLifeTime)
					.boolField("ShotAtAngle", FireworkRocket::isShotAtAngle, FireworkRocket::setShotAtAngle)
					.build();
	
	FireworksComponent getFireworkMeta();
	
	ItemStack getFirework();
	
	boolean isShotAtAngle();
	
	void detonate();
	
	void setFireworkMeta(FireworksComponent meta);
	
	void setFirework(ItemStack firework);
	
	void setShotAtAngle(boolean shotAtAngle);

	void setLifeTime(int ticks);
	
	/**
	 * Returns the time in ticks this rocket is fling
	 * @return ticks
	 */
	int getLifeTime();
	
	void setMaxLifeTime(int ticks);

	/**
	 * Returns the tick this rocket should explode.<br>
	 * Counted by life time
	 * @return max life time
	 */
	int getMaxLifeTime();
	
	@Override
	default NBTCodec<? extends FireworkRocket> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
