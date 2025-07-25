package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Strider extends Animal {
	
	public static final NBTSerializationHandler<Strider>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Strider.class)
					.include(Animal.NBT_HANDLER)
					.intField("BoostTime", Strider::getBoostTime, Strider::setBoostTime, 0)
					.boolField("IsShaking", Strider::isShaking, Strider::setShaking, false)
					.boolField("HasSaddle", Strider::hasSaddle, Strider::setSaddle, false)
					.build();
	
	int getBoostTime();
	
	void setBoostTime(int time);
	
	boolean isShaking();
	
	void setShaking(boolean shaking);
	
	boolean hasSaddle();

	void setSaddle(boolean saddle);
	
	@Override
	default NBTSerializationHandler<? extends Strider> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
