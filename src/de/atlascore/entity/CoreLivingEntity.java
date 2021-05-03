package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LivingEntity;

public class CoreLivingEntity extends CoreEntity implements LivingEntity {

	public CoreLivingEntity(int id, EntityType type, Location loc, UUID uuid) {
		super(id, type, loc, uuid);
	}

	@Override
	public float getHeadPitch() {
		// TODO Auto-generated method stub
		return 0;
	}

}
