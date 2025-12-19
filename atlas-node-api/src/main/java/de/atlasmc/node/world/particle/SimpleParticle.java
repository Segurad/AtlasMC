package de.atlasmc.node.world.particle;

public class SimpleParticle extends AbstractParticle {
	
	public SimpleParticle(ParticleType type) {
		super(type);
	}
	
	public SimpleParticle clone() {
		return this;
	}
	
}
