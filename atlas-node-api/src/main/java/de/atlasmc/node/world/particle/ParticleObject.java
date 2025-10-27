package de.atlasmc.node.world.particle;

import java.lang.reflect.InvocationTargetException;

import org.joml.Vector3f;

import de.atlasmc.node.WorldLocation;
import de.atlasmc.node.entity.Player;

public class ParticleObject implements Animation, Cloneable {

	private final ParticleType particle;
	private final int amount;
	private Object data;
	
	public ParticleObject(ParticleType particle, int amount, Object data) {
		if (particle == null) 
			throw new IllegalArgumentException("Particle can not be null!");
		this.amount = amount;
		this.particle = particle;
		if (data != null && !particle.getData().isAssignableFrom(data.getClass())) 
			throw new IllegalArgumentException("Invalid Data for this Particle!");
		this.data = data;
	}
	
	public ParticleObject(ParticleType effect) {
		this(effect, 1, null);
	}
	
	public ParticleObject(ParticleType effect, int amount) {
		this(effect, amount, null);
	}
	
	public ParticleObject(ParticleType effect, Object data) {
		this(effect, 1, data);
	}

	@Override
	public void play(Player player, WorldLocation loc, Vector3f angle) {
		player.spawnParticle(particle, loc.x, loc.y, loc.z,0 ,0 ,0 ,0 ,amount, this);
	}
	
	@Override
	public void playAll(WorldLocation loc, Vector3f angle) {
		loc.getWorld().spawnParticle(particle, loc, amount);
	}

	public ParticleType getParticle() {
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
