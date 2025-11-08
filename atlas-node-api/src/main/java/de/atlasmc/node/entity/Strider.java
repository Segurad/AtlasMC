package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface Strider extends Animal {
	
	public static final NBTCodec<Strider>
	NBT_HANDLER = NBTCodec
					.builder(Strider.class)
					.include(Animal.NBT_CODEC)
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
	default NBTCodec<? extends Strider> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
