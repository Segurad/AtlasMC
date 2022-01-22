package de.atlascore.entity;

import java.util.UUID;

import de.atlasmc.Vector;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Fireball;
import de.atlasmc.world.World;

public abstract class CoreFireball extends CoreAbstractProjectile implements Fireball {

	public CoreFireball(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	public float getYield() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isIncendiary() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDirection(Vector direction) {
		// TODO Auto-generated method stub
		
	}

}
