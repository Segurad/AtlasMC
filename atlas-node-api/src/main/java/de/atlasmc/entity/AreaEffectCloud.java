package de.atlasmc.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.potion.PotionData;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.world.particle.Particle;

public interface AreaEffectCloud extends Entity {
	
	public static final NBTSerializationHandler<AreaEffectCloud>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AreaEffectCloud.class)
					.include(Entity.NBT_HANDLER)
					.intField("Age", AreaEffectCloud::getAge, AreaEffectCloud::setAge, 0)
					.color("Color", AreaEffectCloud::getColor, AreaEffectCloud::setColor)
					.intField("Duration", AreaEffectCloud::getMaxDuration, AreaEffectCloud::setMaxDuration, 0)
					.intField("DurationOnUse", AreaEffectCloud::getDurationOnUse, AreaEffectCloud::setDurationOnUse, 0)
					.typeList("potion_contents", AreaEffectCloud::hasPotionEffects, AreaEffectCloud::getPotionEffects, PotionEffect.NBT_HANDLER)
					.uuid("Owner", AreaEffectCloud::getOwner, AreaEffectCloud::setOwner)
					.typeCompoundField("custom_particle", AreaEffectCloud::getParticle, AreaEffectCloud::setParticle, Particle.NBT_HANDLER)
					.registryValue("Potion", AreaEffectCloud::getPotionData, AreaEffectCloud::setPotionData, PotionData.getRegistry())
					.floatField("potion_duration_scale", AreaEffectCloud::getPotionDurationScale, AreaEffectCloud::setPotionDurationScale, 1)
					.floatField("Radius", AreaEffectCloud::getRadius, AreaEffectCloud::setRadius, 0.5f)
					.floatField("RadiusOnUse", AreaEffectCloud::getRadiusOnUse, AreaEffectCloud::setRadiusOnUse, 0)
					.floatField("RadiusPerTick", AreaEffectCloud::getRadiusPerTick, AreaEffectCloud::setRadiusPerTick, 0)
					.intField("ReapplicationDelay", AreaEffectCloud::getReapplicationDelay, AreaEffectCloud::setReapplicationDelay)
					.intField("WaitTime", AreaEffectCloud::getInactiveTime, AreaEffectCloud::setInactiveTime)
					.build();
	
	float getPotionDurationScale();
	
	void setPotionDurationScale(float scale);
	
	int getDurationOnUse();
	
	void setDurationOnUse(int duration);
	
	float getRadius();
	
	Color getColor();
	
	boolean getIgnoreRadius();
	
	Particle getParticle();
	
	void setRadius(float radius);
	
	void setColor(Color color);
	
	void setIngnoreRadius(boolean ignore);
	
	void setParticle(Particle particle);

	void setAge(int ticks);
	
	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is active
	 * @return ticks
	 */
	int getAge();

	void setMaxDuration(int ticks);

	/**
	 * Returns the time in ticks until this {@link AreaEffectCloud} despawns or -1 if no time
	 * @return ticks or -1
	 */
	int getMaxDuration();

	void addPotionEffect(PotionEffect potionEffect);
	
	List<PotionEffect> getPotionEffects();
	
	boolean hasPotionEffects();
	
	void removePotionEffect(PotionEffect effect);

	void setOwner(UUID owner);
	
	UUID getOwner();

	void setPotionData(PotionData data);
	
	PotionData getPotionData();

	void setRadiusOnUse(float radius);
	
	/**
	 * Returns the radius change when an PotionEffect is applied to an Entity
	 * @return radius
	 */
	float getRadiusOnUse();

	void setRadiusPerTick(float radius);
	
	/**
	 * Returns the radius change per tick
	 * @return radius
	 */
	float getRadiusPerTick();

	void setReapplicationDelay(int ticks);
	
	/**
	 * Returns the delay in ticks until this {@link AreaEffectCloud} can apply {@link PotionEffect}s to the next Entity
	 * @return ticks
	 */
	int getReapplicationDelay();

	void setInactiveTime(int ticks);
	
	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is inactive
	 * @return ticks
	 */
	int getInactiveTime();
	
	@Override
	default NBTSerializationHandler<? extends AreaEffectCloud> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
