package de.atlasmc.world.particle;

import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.entity.Player;
import de.atlasmc.util.EulerAngle;

public class ParticleObject implements Animation {

	private final Particle particle;
	private final int amount;
	private final Object data;
	
	public ParticleObject(Particle particle, int amount, Object data) {
		this.amount = amount;
		this.particle = particle;
		this.data = data;
	}
	
	@Override
	public void play(Player player, Location loc, EulerAngle angle) {
		player.spawnParticle(particle, loc, amount,0,0,0,10);
	}
	
	@Override
	public void playAll(Location loc, EulerAngle angle) {
		loc.getWorld().spawnParticle(particle, loc, amount);
	}

	public Particle getParticle() {
		return particle;
	}
	
	public Object getData() {
		return data;
	}
	

}
