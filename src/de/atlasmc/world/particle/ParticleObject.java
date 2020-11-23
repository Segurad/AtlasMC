package de.atlasmc.world.particle;

import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.entity.Player;
import de.atlasmc.util.EulerAngle;

public class ParticleObject implements Animation {

	private final Particle particle;
	private final int amount;
	
	public ParticleObject(Particle particle, int amount) {
		this.amount = amount;
		this.particle = particle;
	}
	
	@Override
	public void play(Player player, Location loc, EulerAngle angle) {
		player.spawnParticle(particle, loc, amount,0,0,0,10);
	}
	
	@Override
	public void playAll(Location loc, EulerAngle angle) {
		loc.getWorld().spawnParticle(particle, loc, amount);
	}

}
