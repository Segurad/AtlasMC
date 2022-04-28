package de.atlasmc.potion;

import de.atlasmc.entity.LivingEntity;

public interface PotionEffect extends Cloneable {
	
	public PotionEffect clone();
	
	/**
	 * Called when this PotionEffect is set active for its {@link LivingEntity}
	 */
	public abstract void addEffect(LivingEntity entity);
	
	/**
	 * Called when this PotionEffect is set inactive for its {@link LivingEntity}
	 */
	public abstract void removeEffect(LivingEntity entity);
	
	/**
	 * Returns whether or not this effect will only do something when applied to a entity
	 * @return false if it does not tick or need {@link #removeEffect()}
	 */
	public boolean isOnlyOnApply();
	
	public PotionEffectType getType();
	
	/**
	 * Ticks this {@link PotionEffect}
	 * @param active if false only time will be reduced
	 * @param the entity it ticks for
	 * @return the remaining duration
	 */
	public int tick(LivingEntity entity, boolean active);

	public boolean hasReducedAmbient();

	public int getAmplifier();

	public int getDuration();

	public boolean hasParticels();
	
	public boolean isShowingIcon();

}
