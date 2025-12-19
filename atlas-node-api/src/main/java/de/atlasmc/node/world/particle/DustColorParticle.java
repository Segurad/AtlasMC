package de.atlasmc.node.world.particle;

import de.atlasmc.Color;

public class DustColorParticle extends AbstractParticle {

	private Color color;
	private float scale;
	
	public DustColorParticle(ParticleType type) {
		super(type);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public float getScale() {
		return scale;
	}
	
	public void setScale(float scale) {
		this.scale = scale;
	}

}
