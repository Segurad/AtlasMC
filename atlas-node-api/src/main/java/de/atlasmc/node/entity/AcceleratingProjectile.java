package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface AcceleratingProjectile extends Projectile {
	
	@NotNull
	public static final NBTCodec<AcceleratingProjectile>
	NBT_CODEC = NBTCodec
					.builder(AcceleratingProjectile.class)
					.include(Projectile.NBT_CODEC)
					.doubleField("acceleration_power", AcceleratingProjectile::getAccelerationPower, AcceleratingProjectile::setAccelerationPower)
					.build();
	
	double getAccelerationPower();
	
	void setAccelerationPower(double power);
	
	@Override
	default NBTCodec<? extends AcceleratingProjectile> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
