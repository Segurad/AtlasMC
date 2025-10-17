package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AcceleratingProjectile extends Projectile {
	
	public static final NBTCodec<AcceleratingProjectile>
	NBT_HANDLER = NBTCodec
					.builder(AcceleratingProjectile.class)
					.include(Projectile.NBT_HANDLER)
					.doubleField("acceleration_power", AcceleratingProjectile::getAccelerationPower, AcceleratingProjectile::setAccelerationPower)
					.build();
	
	double getAccelerationPower();
	
	void setAccelerationPower(double power);
	
	@Override
	default NBTCodec<? extends AcceleratingProjectile> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
