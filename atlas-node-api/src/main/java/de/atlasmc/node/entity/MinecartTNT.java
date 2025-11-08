package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface MinecartTNT extends AbstractMinecart {

	public static final NBTCodec<MinecartTNT>
	NBT_HANDLER = NBTCodec
					.builder(MinecartTNT.class)
					.include(AbstractMinecart.NBT_HANDLER)
					.intField("fuse", MinecartTNT::getFuseTime, MinecartTNT::setFuseTime, -1)
					.floatField("explosion_power", MinecartTNT::getExplosionPower, MinecartTNT::setExplosionPower, 4)
					.floatField("explosion_speed_factor", MinecartTNT::getExplosionSpeedFactor, MinecartTNT::setExplosionSpeedFactor, 1)
					.build();
	
	void setFuseTime(int ticks);
	
	/**
	 * Returns the time in ticks until the TNT explodes or -1 if it is not fusing
	 * @return ticks
	 */
	int getFuseTime();
	
	float getExplosionPower();
	
	void setExplosionPower(float power);
	
	float getExplosionSpeedFactor();
	
	void setExplosionSpeedFactor(float speedFactor);

	@Override
	default NBTCodec<? extends AbstractMinecart> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
