package de.atlasmc.node.event.entity;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.Projectile;
import de.atlasmc.node.event.ServerHandlerList;
import de.atlasmc.node.world.World;

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
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

}
