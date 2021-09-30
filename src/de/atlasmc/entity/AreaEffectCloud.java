package de.atlasmc.entity;

import de.atlasmc.world.particle.ParticleObject;

public interface AreaEffectCloud extends Entity {
	
	public float getRadius();
	
	public int getColor();
	
	public boolean getIgnoreRadius();
	
	public ParticleObject getParticle();
	
	public void setRadius(float radius);
	
	public void setColor(int color);
	
	public void setIngnoreRadius(boolean ignore);
	
	public void setParticle(ParticleObject particle);
	
}
