package de.atlasmc.node.world.particle;

import de.atlasmc.Color;

public class DustColorTransitionParticle extends AbstractParticle {
	
	private Color from;
	private Color to;
	private float scale;

	public DustColorTransitionParticle(ParticleType type) {
		super(type);
	}
	
	public Color getFrom() {
		return from;
	}
	
	public Color getTo() {
		return to;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setFrom(Color from) {
		this.from = from;
	}
	
	public void setTo(Color to) {
		this.to = to;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}
	
}
