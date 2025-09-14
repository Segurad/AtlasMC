package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.FireworksComponent;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FireworkRocket extends Projectile {
	
	public static final NBTSerializationHandler<FireworkRocket>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends FireworkRocket> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
