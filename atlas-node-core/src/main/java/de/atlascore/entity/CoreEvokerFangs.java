package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.EvokerFangs;

public class CoreEvokerFangs extends CoreEntity implements EvokerFangs {

	private Entity caster;
	private UUID casterUUID;
	private int warmup;
	
	public CoreEvokerFangs(EntityType type) {
		super(type);
	}

	@Override
	public void setCaster(Entity caster) {
		this.caster = caster;
	}

	@Override
	public Entity getCaster() {
		return caster;
	}

	@Override
	public UUID getCasterUUID() {
		return casterUUID;
	}

	@Override
	public void setCasterUUID(UUID uuid) {
		this.casterUUID = uuid;
	}

	@Override
	public int getWarmup() {
		return warmup;
	}

	@Override
	public void setWarmup(int warmup) {
		this.warmup = warmup;
	}

}
