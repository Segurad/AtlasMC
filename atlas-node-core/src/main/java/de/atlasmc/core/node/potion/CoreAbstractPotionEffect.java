package de.atlasmc.core.node.potion;

import java.util.UUID;

import de.atlasmc.node.entity.LivingEntity;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public abstract class CoreAbstractPotionEffect implements PotionEffect {
	
	public static final NBTSerializationHandler<CoreAbstractPotionEffect>
	NBT_HANDLER = NBTSerializationHandler
					.builder(CoreAbstractPotionEffect.class)
					.include(PotionEffect.NBT_HANDLER)
					.byteField("amplifier", CoreAbstractPotionEffect::getAmplifier, CoreAbstractPotionEffect::setAmplifier, (byte) 0)
					.intField("duration", CoreAbstractPotionEffect::getDuration, CoreAbstractPotionEffect::setDuration, 1)
					.boolField("ambient", CoreAbstractPotionEffect::hasReducedAmbient, CoreAbstractPotionEffect::setReducedAmbient, false)
					.boolField("show_particles", CoreAbstractPotionEffect::hasParticels, CoreAbstractPotionEffect::setParticles, true)
					.boolField("show_icon", CoreAbstractPotionEffect::isShowingIcon, CoreAbstractPotionEffect::setIcon, true)
					.uuid("uuid", CoreAbstractPotionEffect::getUUID, CoreAbstractPotionEffect::setUUID)
					.build();
	
	private PotionEffectType type;
	private boolean icon;
	private boolean particles;
	private boolean reducedAmbient;
	private UUID uuid;
	private int amplifier;
	protected int duration;
	
	public CoreAbstractPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon, UUID uuid) {
		this.type = type;
		this.icon = icon;
		this.particles = particles;
		this.reducedAmbient = reducedAmbient;
		this.amplifier = amplifier;
		this.duration = duration;
		this.uuid = uuid;
	}
	
	@Override
	public CoreAbstractPotionEffect clone() {
		try {
			return (CoreAbstractPotionEffect) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int tick(LivingEntity entity, boolean active) {
		return --duration;
	}
	
	@Override
	public boolean isOnlyOnApply() {
		return false;
	}

	@Override
	public PotionEffectType getType() {
		return type;
	}

	@Override
	public boolean hasReducedAmbient() {
		return reducedAmbient;
	}

	@Override
	public int getAmplifier() {
		return amplifier;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public boolean hasParticels() {
		return particles;
	}

	@Override
	public boolean isShowingIcon() {
		return icon;
	}
	
	@Override
	public UUID getUUID() {
		return uuid;
	}
	
	private void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}
	
	private void setDuration(int duration) {
		this.duration = duration;
	}
	
	private void setReducedAmbient(boolean reducedAmbient) {
		this.reducedAmbient = reducedAmbient;
	}
	
	private void setParticles(boolean particles) {
		this.particles = particles;
	}
	
	private void setIcon(boolean icon) {
		this.icon = icon;
	}
	
	private void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

}
