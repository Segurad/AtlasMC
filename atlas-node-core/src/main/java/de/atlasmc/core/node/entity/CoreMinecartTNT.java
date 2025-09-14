package de.atlasmc.core.node.entity;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.MinecartTNT;

public class CoreMinecartTNT extends CoreAbstractMinecart implements MinecartTNT {

	private int fuseTime = -1;
	private float explosionPower = 4;
	private float explosionSpeedFactor = 1;
	
	public CoreMinecartTNT(EntityType type) {
		super(type);
	}

	@Override
	public void setFuseTime(int ticks) {
		this.fuseTime = ticks;
	}

	@Override
	public int getFuseTime() {
		return fuseTime;
	}

	@Override
	public float getExplosionPower() {
		return explosionPower;
	}

	@Override
	public void setExplosionPower(float power) {
		this.explosionPower = power; 
	}

	@Override
	public float getExplosionSpeedFactor() {
		return explosionSpeedFactor;
	}

	@Override
	public void setExplosionSpeedFactor(float speedFactor) {
		this.explosionSpeedFactor = speedFactor;
	}

}
