package de.atlasmc.node;

import org.joml.Vector3d;

import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Projectile;

public interface ProjectileSource {
	
	Projectile launchProjectile(Projectile projectile, Vector3d velocity);
	
	Projectile launchProjectile(EntityType type, Vector3d velocity);
	
	default Projectile launchProjectile(Projectile projectile) {
		return launchProjectile(projectile, null);
	}
	
	default Projectile launchProjectile(EntityType type) {
		return launchProjectile(type, null);
	}

}
