package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ShulkerBullet;

public class CoreShulkerBullet extends CoreAbstractProjectile implements ShulkerBullet {

	private Entity target;
	private UUID targetUUID;
	
	public CoreShulkerBullet(EntityType type) {
		super(type);
	}

	@Override
	public void setTarget(Entity target) {
		this.target = target;
	}

	@Override
	public Entity getTarget() {
		return target;
	}

	@Override
	public void setTargetUUID(UUID uuid) {
		this.targetUUID = uuid;
	}

	@Override
	public UUID getTargetUUID() {
		return targetUUID;
	}

}
