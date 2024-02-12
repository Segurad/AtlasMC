package de.atlasmc;

import org.joml.Vector3d;

import de.atlasmc.entity.Projectile;
import de.atlasmc.entity.Projectile.ProjectileType;

public interface ProjectileSource {
	
	public Projectile lounchProjectile(Projectile projectile, Vector3d velocity);
	
	public Projectile lounchProjectile(ProjectileType type, Vector3d velocity);
	
	public default Projectile lounchProjectile(Projectile projectile) {
		return lounchProjectile(projectile, null);
	}
	
	public default Projectile lounchProjectile(ProjectileType type) {
		return lounchProjectile(type, null);
	}

}
