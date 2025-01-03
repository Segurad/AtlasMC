package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.ShulkerBullet;

public class CoreShulkerBullet extends CoreAbstractProjectile implements ShulkerBullet {

	private Entity target;
	
	public CoreShulkerBullet(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	public void setTarget(Entity target) {
		this.target = target;
	}

	@Override
	public Entity getTarget() {
		return target;
	}

}
