package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.entity.Projectile;
import de.atlasmc.world.World;

public class ProjectileLounchEvent extends EntitySpawnEvent {

	public ProjectileLounchEvent(Entity entity, World world, double x, double y, double z, float pitch, float yaw) {
		super(entity, world, x, y, z, pitch, yaw);
		if (!(entity instanceof Projectile))
			throw new IllegalArgumentException("Entity must be instance of Projectile!");
	}
	
	@Override
	public Projectile getEntity() {
		return (Projectile) super.getEntity();
	}

}
