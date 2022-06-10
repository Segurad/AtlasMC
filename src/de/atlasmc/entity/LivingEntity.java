package de.atlasmc.entity;

import java.util.Collection;
import de.atlascore.inventory.EntityEquipment;
import de.atlasmc.Color;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.Attributeable;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface LivingEntity extends Damageable, Attributeable {

	public float getHeadPitch();
	
	public int getDisplayedArrows();
	
	public int getDisplayedBeeStingers();
	
	public void setDisplayedArrows(int arrows);
	
	public void setDisplayedBeeStringers(int stingers);
	
	/**
	 * Returns the Color value of particles spawned through {@link PotionEffect}s<br>
	 * if non particle effect is active the {@link Color} of 0x000000 will be returned
	 * @return color
	 */
	public Color getPotionAmbientColor();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 and null will display no particles
	 * @param color
	 */
	public void setPotionAmbientColor(Color color);
	
	/**
	 * Returns the RGB value of particles spawned through {@link PotionEffect}s
	 * @return rgb or 0 if no particle
	 */
	public int getPotionAmbientColorRGB();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 will display no particles
	 * @param rgb
	 */
	public void setPotionAmbientColor(int rgb);

	/**
	 * Whether or not the ambient particles are reduced to 1/5
	 * @return true if reduced
	 */
	public boolean isPotionAmbientReduced();
	
	/**
	 * Sets whether or not the ambient particles should be reduced to 1/5
	 * @param reduced
	 */
	public void setPotionAmbientReduced(boolean reduced);

	public float getAbsorption();
	
	public void setAbsorption(float absorption);

	public void addPotionEffect(PotionEffectType type, int amplifier, int duration);
	
	public void addPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon);
	
	/**
	 * Creates a copy of the effect and applies it to this Entity
	 * @param effect
	 */
	public void addPotionEffect(PotionEffect effect);
	
	public Collection<PotionEffect> getActivePotionEffects();
	
	public boolean hasPotionEffects();
	
	public boolean hasPotionEffect(PotionEffectType type);
	
	public PotionEffect getPotionEffect(PotionEffectType type);
	
	public void removePotionEffect(PotionEffectType type);
	
	public AttributeInstance getAttribute(Attribute attribute);
	
	public boolean hasAttribute(Attribute attribute);

	/**
	 * Time in ticks the death animation is played<br>
	 * 0 if alive
	 * @param time
	 */
	public void setDeathAnimationTime(int time);

	public void setFallFlying(boolean fallFlying);

	public void setHurtAnimationTime(int time);

	public boolean hasAttributes();

	public int getDeathAnimationTime();

	public boolean isFallFlying();

	public int getHurtAnimationTime();

	public void setRemoveWhenFarAway(boolean remove);
	
	public boolean getRemoveWhenFarAway();

	public EntityEquipment getEquipment();

	/**
	 * Sets the time in ticks this entity is invulnerable after it took damage
	 * @param time
	 */
	public void setAttackTime(int time);
	
	/**
	 * Returns the time in ticks this entity is invulnerable after it took damage<br>
	 * 0 when no damage occurred in the last time
	 * @return time
	 */
	public int getAttackTime();
	
}
