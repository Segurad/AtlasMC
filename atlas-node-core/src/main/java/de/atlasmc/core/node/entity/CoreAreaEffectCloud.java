package de.atlasmc.core.node.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.atlasmc.Color;
import de.atlasmc.node.entity.AreaEffectCloud;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.potion.PotionData;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.world.particle.Particle;

public class CoreAreaEffectCloud extends CoreEntity implements AreaEffectCloud {
	
	protected static final MetaDataField<Float> 
	META_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, 0.5f, MetaDataType.FLOAT);
	protected static final MetaDataField<Integer>
	META_COLOR = new MetaDataField<>(CoreEntity.LAST_META_INDEX+2, 0, MetaDataType.VAR_INT);
	protected static final MetaDataField<Boolean>
	META_IGNORE_RADIUS = new MetaDataField<>(CoreEntity.LAST_META_INDEX+3, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Particle>
	META_PARTICLE = new MetaDataField<>(CoreEntity.LAST_META_INDEX + 4, null, MetaDataType.PARTICLE);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+4;
	
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
	
	public CoreAreaEffectCloud(EntityType type) {
		super(type);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_RADIUS);
		metaContainer.set(META_COLOR);
		metaContainer.set(META_IGNORE_RADIUS);
		metaContainer.set(META_PARTICLE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public float getRadius() {
		return metaContainer.getData(META_RADIUS);
	}

	@Override
	public Color getColor() {
		return Color.fromARGB(metaContainer.getData(META_COLOR));
	}

	@Override
	public boolean getIgnoreRadius() {
		return metaContainer.getData(META_IGNORE_RADIUS);
	}

	@Override
	public Particle getParticle() {
		return metaContainer.getData(META_PARTICLE);
	}

	@Override
	public void setRadius(float radius) {
		metaContainer.get(META_RADIUS).setData(radius);
	}

	@Override
	public void setColor(Color color) {
		metaContainer.get(META_COLOR).setData(color.asARGB());
	}

	@Override
	public void setIngnoreRadius(boolean ignore) {
		metaContainer.get(META_IGNORE_RADIUS).setData(ignore);
	}

	@Override
	public void setParticle(Particle particle) {
		metaContainer.get(META_PARTICLE).setData(particle);
	}

	@Override
	public void setAge(int ticks) {
		this.age = ticks;
	}

	@Override
	public int getAge() {
		return age;
	}

	@Override
	public void setMaxDuration(int ticks) {
		this.maxDuration = ticks;
	}

	@Override
	public int getMaxDuration() {
		return maxDuration;
	}

	@Override
	public void addPotionEffect(PotionEffect potionEffect) {
		getPotionEffects().add(potionEffect);
	}

	@Override
	public List<PotionEffect> getPotionEffects() {
		if (potionEffects == null)
			potionEffects = new ArrayList<>();
		return potionEffects;
	}

	@Override
	public boolean hasPotionEffects() {
		return potionEffects != null && !potionEffects.isEmpty();
	}

	@Override
	public void removePotionEffect(PotionEffect effect) {
		if (!hasPotionEffects())
			return;
		getPotionEffects().remove(effect);
	}

	@Override
	public void setOwner(UUID owner) {
		this.owner = owner;
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public void setPotionData(PotionData data) {
		this.data = data;
	}

	@Override
	public PotionData getPotionData() {
		return data;
	}

	@Override
	public void setRadiusOnUse(float radius) {
		this.radiusOnUse = radius;
	}

	@Override
	public float getRadiusOnUse() {
		return radiusOnUse;
	}

	@Override
	public void setRadiusPerTick(float radius) {
		this.radiusPerTick = radius;
	}

	@Override
	public float getRadiusPerTick() {
		return radiusPerTick;
	}

	@Override
	public void setReapplicationDelay(int ticks) {
		this.reapplicationDelay = ticks;
	}

	@Override
	public int getReapplicationDelay() {
		return reapplicationDelay;
	}

	@Override
	public void setInactiveTime(int ticks) {
		this.inactiveTime = ticks;
	}

	@Override
	public int getInactiveTime() {
		return inactiveTime;
	}

	@Override
	public float getPotionDurationScale() {
		return potionDurationScale;
	}

	@Override
	public void setPotionDurationScale(float scale) {
		this.potionDurationScale = scale;
	}

	@Override
	public int getDurationOnUse() {
		return durationOnUse;
	}

	@Override
	public void setDurationOnUse(int duration) {
		this.durationOnUse = duration;
	}

}
