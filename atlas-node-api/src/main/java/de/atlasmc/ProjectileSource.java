package de.atlasmc;

import org.joml.Vector3d;

import de.atlasmc.entity.Projectile;
import de.atlasmc.entity.Projectile.ProjectileType;

public interface ProjectileSource {
	
	Projectile lounchProjectile(Projectile projectile, Vector3d velocity);
	
	Projectile lounchProjectile(ProjectileType type, Vector3d velocity);
	
	default Projectile lounchProjectile(Projectile projectile) {
		return lounchProjectile(projectile, null);
	}
	
	default Projectile lounchProjectile(ProjectileType type) {
		return lounchProjectile(type, null);
	}

}
