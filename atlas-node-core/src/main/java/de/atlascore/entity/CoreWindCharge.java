package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.WindCharge;
import de.atlasmc.entity.EntityType;

public class CoreWindCharge extends CoreAbstractProjectile implements WindCharge {

	protected double accelerationPower;
	
	public CoreWindCharge(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public double getAccelerationPower() {
		return accelerationPower;
	}

	@Override
	public void setAccelerationPower(double power) {
		this.accelerationPower = power;
	}

}
