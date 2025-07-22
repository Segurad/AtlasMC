package de.atlasmc.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AcceleratingProjectile extends Projectile {
	
	public static final NBTSerializationHandler<AcceleratingProjectile>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AcceleratingProjectile.class)
					.include(Projectile.NBT_HANDLER)
					.doubleField("acceleration_power", AcceleratingProjectile::getAccelerationPower, AcceleratingProjectile::setAccelerationPower)
					.build();
	
	double getAccelerationPower();
	
	void setAccelerationPower(double power);
	
	@Override
	default NBTSerializationHandler<? extends AcceleratingProjectile> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
