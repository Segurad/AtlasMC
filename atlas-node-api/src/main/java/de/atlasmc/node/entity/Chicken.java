package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Chicken extends Animal {
	
	public static final NBTCodec<Chicken>
	NBT_HANDLER = NBTCodec
					.builder(Chicken.class)
					.include(Animal.NBT_CODEC)
					.intField("EggLayTime", Chicken::getEggLayTime, Chicken::setEggLayTime, -1)
					.boolField("IsChickenJockey", Chicken::isChickenJockey, Chicken::setChickenJocken, false)
					// variant
					.build();

	void setEggLayTime(int time);
	
	/**
	 * Returns time in ticks until the Chicken lays a egg or -1 if no active timer
	 * @return ticks or -1
	 */
	int getEggLayTime();
	
	boolean isChickenJockey();
	
	void setChickenJocken(boolean value);

}
