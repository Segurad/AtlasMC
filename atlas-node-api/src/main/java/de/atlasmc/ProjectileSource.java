package de.atlasmc;

import org.joml.Vector3d;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Projectile;

public interface ProjectileSource {
	
	Projectile lounchProjectile(Projectile projectile, Vector3d velocity);
	
	Projectile lounchProjectile(EntityType type, Vector3d velocity);
	
	default Projectile lounchProjectile(Projectile projectile) {
		return lounchProjectile(projectile, null);
	}
	
	default Projectile lounchProjectile(EntityType type) {
		return lounchProjectile(type, null);
	}

}
