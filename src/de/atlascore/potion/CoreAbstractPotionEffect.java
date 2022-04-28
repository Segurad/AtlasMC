package de.atlascore.potion;

import de.atlasmc.entity.LivingEntity;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

public abstract class CoreAbstractPotionEffect implements PotionEffect {
	
	private final PotionEffectType type;
	private final boolean icon;
	private final boolean particles;
	private final boolean reducedAmbient;
	protected final int amplifier;
	protected int duration;
	
	public CoreAbstractPotionEffect(PotionEffectType type, int amplifier, int duration, boolean reducedAmbient, boolean particles, boolean icon) {
		this.type = type;
		this.icon = icon;
		this.particles = particles;
		this.reducedAmbient = reducedAmbient;
		this.amplifier = amplifier;
		this.duration = duration;
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

}
