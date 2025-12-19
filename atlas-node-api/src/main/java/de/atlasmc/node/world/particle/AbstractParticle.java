package de.atlasmc.node.world.particle;

import de.atlasmc.util.CloneException;

public abstract class AbstractParticle implements Particle {

	protected final ParticleType type;
	
	public AbstractParticle(ParticleType type) {
		this.type = type;
	}
	
	@Override
	public ParticleType getType() {
		return type;
	}
	
	public AbstractParticle clone() {
		try {
			return (AbstractParticle) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException();
		}
	}
	
}
