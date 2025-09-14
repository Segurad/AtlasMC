package de.atlasmc.core.node.entity;

import java.util.UUID;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.ShulkerBullet;

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
