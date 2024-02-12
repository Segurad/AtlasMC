package de.atlasmc.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
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

	public void setAge(int ticks);
	
	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is active
	 * @return ticks
	 */
	public int getAge();

	public void setMaxDuration(int ticks);

	/**
	 * Returns the time in ticks until this {@link AreaEffectCloud} despawns or -1 if no time
	 * @return ticks or -1
	 */
	public int getMaxDuration();

	public void addPotionEffect(PotionEffect potionEffect);
	
	public List<PotionEffect> getPotionEffects();
	
	public boolean hasPotionEffects();
	
	public void removePotionEffect(PotionEffect effect);

	public void setOwner(UUID owner);
	
	public UUID getOwner();

	public void setPotionData(PotionData data);
	
	public PotionData getPotionData();

	public void setRadiusOnUse(float radius);
	
	/**
	 * Returns the radius change when an PotionEffect is applied to an Entity
	 * @return radius
	 */
	public float getRadiusOnUse();

	public void setRadiusPerTick(float radius);
	
	/**
	 * Returns the radius change per tick
	 * @return radius
	 */
	public float getRadiusPerTick();

	public void setReapplicationDelay(int ticks);
	
	/**
	 * Returns the delay in ticks until this {@link AreaEffectCloud} can apply {@link PotionEffect}s to the next Entity
	 * @return ticks
	 */
	public int getReapplicationDelay();

	public void setInactiveTime(int ticks);
	
	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is inactive
	 * @return ticks
	 */
	public int getInactiveTime();
	
}
