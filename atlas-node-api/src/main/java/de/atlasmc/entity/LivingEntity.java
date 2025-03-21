package de.atlasmc.entity;

import java.util.Collection;
import java.util.List;

import de.atlasmc.Color;
import de.atlasmc.Location;
import de.atlasmc.ProjectileSource;
import de.atlasmc.SimpleLocation;
import de.atlasmc.attribute.Attribute;
import de.atlasmc.attribute.AttributeInstance;
import de.atlasmc.attribute.Attributeable;
import de.atlasmc.inventory.EntityEquipment;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public interface LivingEntity extends Damageable, Attributeable, ProjectileSource {

	float getHeadPitch();
	
	int getDisplayedArrows();
	
	int getDisplayedBeeStingers();
	
	void setDisplayedArrows(int arrows);
	
	void setDisplayedBeeStringers(int stingers);
	
	/**
	 * Returns the Color value of particles spawned through {@link PotionEffect}s<br>
	 * if non particle effect is active the {@link Color} of 0x000000 will be returned
	 * @return color
	 */
	Color getPotionAmbientColor();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 and null will display no particles
	 * @param color
	 */
	void setPotionAmbientColor(Color color);
	
	/**
	 * Returns the RGB value of particles spawned through {@link PotionEffect}s
	 * @return rgb or 0 if no particle
	 */
	int getPotionAmbientColorRGB();
	
	/**
	 * Sets the Color of ambient particles caused by a {@link PotionEffect}<br>
	 * 0x000000 will display no particles
	 * @param rgb
	 */
	void setPotionAmbientColor(int rgb);

	/**
	 * Whether or not the ambient particles are reduced to 1/5
	 * @return true if reduced
	 */
	boolean isPotionAmbientReduced();
	
	/**
	 * Sets whether or not the ambient particles should be reduced to 1/5
	 * @param reduced
	 */
	void setPotionAmbientReduced(boolean reduced);

	float getAbsorption();
	
	void setAbsorption(float absorption);

	void addPotionEffect(PotionEffectType type, int amplifier, int duration);
	
	void addPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon);
	
	/**
	 * Creates a copy of the effect and applies it to this Entity
	 * @param effect
	 */
	void addPotionEffect(PotionEffect effect);
	
	void addPotionEffects(List<PotionEffect> effects);
	
	Collection<PotionEffect> getActivePotionEffects();
	
	boolean hasPotionEffects();
	
	boolean hasPotionEffect(PotionEffectType type);
	
	PotionEffect getPotionEffect(PotionEffectType type);
	
	void removePotionEffect(PotionEffectType type);
	
	void removePotionEffects();
	
	AttributeInstance getAttribute(Attribute attribute);
	
	boolean hasAttribute(Attribute attribute);

	/**
	 * Time in ticks the death animation is played<br>
	 * 0 if alive
	 * @param time
	 */
	void setDeathAnimationTime(int time);

	void setFallFlying(boolean fallFlying);

	void setHurtAnimationTime(int time);

	boolean hasAttributes();

	int getDeathAnimationTime();

	boolean isFallFlying();

	int getHurtAnimationTime();

	void setRemoveWhenFarAway(boolean remove);
	
	boolean getRemoveWhenFarAway();

	EntityEquipment getEquipment();

	/**
	 * Sets the time in ticks this entity is invulnerable after it took damage
	 * @param time
	 */
	void setAttackTime(int time);
	
	/**
	 * Returns the time in ticks this entity is invulnerable after it took damage<br>
	 * 0 when no damage occurred in the last time
	 * @return time
	 */
	int getAttackTime();
	
	double getEyeHeight();
	
	Location getEyeLocation();
	
	Location getEyeLocation(Location location);
	
	SimpleLocation getEyeLocation(SimpleLocation location);
	
}
