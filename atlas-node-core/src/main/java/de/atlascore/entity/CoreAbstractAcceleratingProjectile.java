package de.atlascore.entity;

import de.atlasmc.entity.AcceleratingProjectile;
import de.atlasmc.entity.EntityType;

public abstract class CoreAbstractAcceleratingProjectile extends CoreAbstractProjectile implements AcceleratingProjectile {
	
	protected double acceleration_power;
	
	public CoreAbstractAcceleratingProjectile(EntityType type) {
		super(type);
	}
	
	@Override
	public double getAccelerationPower() {
		return acceleration_power;
	}
	
	@Override
	public void setAccelerationPower(double power) {
		this.acceleration_power = power;
	}
	
}
