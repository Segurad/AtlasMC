package de.atlascore.entity;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Ravager;

public class CoreRavager extends CoreRaider implements Ravager {
	
	private int cooldownAttack;
	private int timeRoar;
	private int timeStunned;

	public CoreRavager(EntityType type) {
		super(type);
	}

	@Override
	public void setAttackCooldown(int ticks) {
		this.cooldownAttack = ticks;
	}

	@Override
	public int getAttackCooldown() {
		return cooldownAttack;
	}

	@Override
	public void setRoarTime(int ticks) {
		this.timeRoar = ticks;
	}

	@Override
	public int getRoarTime() {
		return timeRoar;
	}

	@Override
	public void setStunTime(int ticks) {
		this.timeStunned = ticks;
	}

	@Override
	public int getStunTime() {
		return timeStunned;
	}
	
}
