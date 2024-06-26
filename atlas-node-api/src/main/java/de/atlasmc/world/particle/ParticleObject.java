package de.atlasmc.world.particle;

import java.lang.reflect.InvocationTargetException;

import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.entity.Player;
import de.atlasmc.util.EulerAngle;

public class ParticleObject implements Animation, Cloneable {

	private final Particle particle;
	private final int amount;
	private Object data;
	
	public ParticleObject(Particle particle, int amount, Object data) {
		if (particle == null) 
			throw new IllegalArgumentException("Particle can not be null!");
		this.amount = amount;
		this.particle = particle;
		if (data != null && !particle.getData().isAssignableFrom(data.getClass())) 
			throw new IllegalArgumentException("Invalid Data for this Particle!");
		this.data = data;
	}
	
	public ParticleObject(Particle effect) {
		this(effect, 1, null);
	}
	
	public ParticleObject(Particle effect, int amount) {
		this(effect, amount, null);
	}
	
	public ParticleObject(Particle effect, Object data) {
		this(effect, 1, data);
	}

	@Override
	public void play(Player player, Location loc, EulerAngle angle) {
		player.spawnParticle(particle, loc.x, loc.y, loc.z,0 ,0 ,0 ,0 ,amount, this);
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
	
	public ParticleObject clone() {
		ParticleObject clone = null;
		try {
			clone = (ParticleObject) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (clone == null)
			return null;
		if (data != null && data instanceof Cloneable)
			try {
				clone.data = data.getClass().getMethod("clone").invoke(data);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		return clone;
	}
	

}
