package de.atlasmc.entity;

import de.atlasmc.Particle;

public interface AreaEffectCloud extends Entity {
	
	public float getRadius();
	public int getColor();
	public boolean getIgnoreRadius();
	public Particle getParticle();
	
}
