package de.atlasmc.node.entity.component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.potion.PotionData;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.world.particle.Particle;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public class AreaEffectCloudMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<AreaEffectCloudMetaComponent>
	NBT_HANDLER = NBTCodec
					.builder(AreaEffectCloudMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.intField("Age", AreaEffectCloudMetaComponent::getAge, AreaEffectCloudMetaComponent::setAge, 0)
					.intField("Duration", AreaEffectCloudMetaComponent::getMaxDuration, AreaEffectCloudMetaComponent::setMaxDuration, 0)
					.intField("DurationOnUse", AreaEffectCloudMetaComponent::getDurationOnUse, AreaEffectCloudMetaComponent::setDurationOnUse, 0)
					.codecList("potion_contents", AreaEffectCloudMetaComponent::hasPotionEffects, AreaEffectCloudMetaComponent::getPotionEffects, PotionEffect.NBT_CODEC)
					.codec("Owner", AreaEffectCloudMetaComponent::getOwner, AreaEffectCloudMetaComponent::setOwner, NBTCodecs.UUID_CODEC)
					.codec("custom_particle", AreaEffectCloudMetaComponent::getParticle, AreaEffectCloudMetaComponent::setParticle, Particle.NBT_CODEC)
					.codec("Potion", AreaEffectCloudMetaComponent::getPotionData, AreaEffectCloudMetaComponent::setPotionData, Registries.registryValueNBTCodec(PotionData.REGISTRY_KEY))
					.floatField("potion_duration_scale", AreaEffectCloudMetaComponent::getPotionDurationScale, AreaEffectCloudMetaComponent::setPotionDurationScale, 1)
					.floatField("Radius", AreaEffectCloudMetaComponent::getRadius, AreaEffectCloudMetaComponent::setRadius, 0.5f)
					.floatField("RadiusOnUse", AreaEffectCloudMetaComponent::getRadiusOnUse, AreaEffectCloudMetaComponent::setRadiusOnUse, 0)
					.floatField("RadiusPerTick", AreaEffectCloudMetaComponent::getRadiusPerTick, AreaEffectCloudMetaComponent::setRadiusPerTick, 0)
					.intField("ReapplicationDelay", AreaEffectCloudMetaComponent::getReapplicationDelay, AreaEffectCloudMetaComponent::setReapplicationDelay)
					.intField("WaitTime", AreaEffectCloudMetaComponent::getInactiveTime, AreaEffectCloudMetaComponent::setInactiveTime)
					.build();
	
	public static final MetaDataField<Float> 
	META_RADIUS = new MetaDataField<>(8, 3.0f, EntityMetaTypes.FLOAT);
	public static final MetaDataField<Boolean>
	META_IGNORE_RADIUS = new MetaDataField<>(9, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Particle>
	META_PARTICLE = new MetaDataField<>(10, null, EntityMetaTypes.PARTICLE);
	
	private int age;
	private int maxDuration;
	private float radiusPerTick;
	private float radiusOnUse;
	private float potionDurationScale;
	private int durationOnUse;
	private int reapplicationDelay;
	private int inactiveTime;
	private UUID owner;
	private List<PotionEffect> potionEffects;
	private PotionData data;
	
	public AreaEffectCloudMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_RADIUS);
		container.set(META_IGNORE_RADIUS);
		container.set(META_PARTICLE);
	}

	public float getRadius() {
		return getHolder().getMetaContainer().getData(META_RADIUS);
	}

	public boolean getIgnoreRadius() {
		return getHolder().getMetaContainer().getData(META_IGNORE_RADIUS);
	}

	public Particle getParticle() {
		return getHolder().getMetaContainer().getData(META_PARTICLE);
	}

	public void setRadius(float radius) {
		getHolder().getMetaContainer().setData(META_RADIUS, radius);
	}

	public void setIngnoreRadius(boolean ignore) {
		getHolder().getMetaContainer().setData(META_IGNORE_RADIUS, ignore);
	}

	public void setParticle(Particle particle) {
		getHolder().getMetaContainer().setData(META_PARTICLE, particle);
	}

	public void setAge(int ticks) {
		this.age = ticks;
	}

	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is active
	 * @return ticks
	 */
	public int getAge() {
		return age;
	}

	public void setMaxDuration(int ticks) {
		this.maxDuration = ticks;
	}

	/**
	 * Returns the time in ticks until this {@link AreaEffectCloud} despawns or -1 if no time
	 * @return ticks or -1
	 */
	public int getMaxDuration() {
		return maxDuration;
	}

	public void addPotionEffect(PotionEffect potionEffect) {
		getPotionEffects().add(potionEffect);
	}

	public List<PotionEffect> getPotionEffects() {
		if (potionEffects == null)
			potionEffects = new ArrayList<>();
		return potionEffects;
	}

	public boolean hasPotionEffects() {
		return potionEffects != null && !potionEffects.isEmpty();
	}

	public void removePotionEffect(PotionEffect effect) {
		if (!hasPotionEffects())
			return;
		getPotionEffects().remove(effect);
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	public UUID getOwner() {
		return owner;
	}

	public void setPotionData(PotionData data) {
		this.data = data;
	}

	public PotionData getPotionData() {
		return data;
	}

	public void setRadiusOnUse(float radius) {
		this.radiusOnUse = radius;
	}

	/**
	 * Returns the radius change when an PotionEffect is applied to an Entity
	 * @return radius
	 */
	public float getRadiusOnUse() {
		return radiusOnUse;
	}

	public void setRadiusPerTick(float radius) {
		this.radiusPerTick = radius;
	}

	/**
	 * Returns the radius change per tick
	 * @return radius
	 */
	public float getRadiusPerTick() {
		return radiusPerTick;
	}

	public void setReapplicationDelay(int ticks) {
		this.reapplicationDelay = ticks;
	}

	/**
	 * Returns the delay in ticks until this {@link AreaEffectCloud} can apply {@link PotionEffect}s to the next Entity
	 * @return ticks
	 */
	public int getReapplicationDelay() {
		return reapplicationDelay;
	}

	public void setInactiveTime(int ticks) {
		this.inactiveTime = ticks;
	}

	/**
	 * Returns the time in ticks this {@link AreaEffectCloud} is inactive
	 * @return ticks
	 */
	public int getInactiveTime() {
		return inactiveTime;
	}

	public float getPotionDurationScale() {
		return potionDurationScale;
	}

	public void setPotionDurationScale(float scale) {
		this.potionDurationScale = scale;
	}

	public int getDurationOnUse() {
		return durationOnUse;
	}

	public void setDurationOnUse(int duration) {
		this.durationOnUse = duration;
	}
	
	@Override
	public NBTCodec<? extends AreaEffectCloudMetaComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
