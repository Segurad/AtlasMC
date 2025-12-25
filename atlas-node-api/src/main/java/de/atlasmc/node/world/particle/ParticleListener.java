package de.atlasmc.node.world.particle;

import org.joml.Vector3d;
import org.joml.Vector3f;

public interface ParticleListener {

	default void spawnParticle(Particle particle, Vector3d loc, float particledata) {
		spawnParticle(particle, loc, null, particledata, 1);
	}

	default void spawnParticle(Particle particle, Vector3d loc, float particledata, int count) {
		spawnParticle(particle, loc, null, particledata, count);
	}
	
	void spawnParticle(Particle particle, Vector3d loc, Vector3f off, float maxSpeed, int count);
	
}
