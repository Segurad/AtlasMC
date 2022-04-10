package de.atlasmc.potion;

public class PotionEffect implements Cloneable {
	
	private final PotionEffectType type;
	private int duration;
	private final int amplifier;
	private final boolean reduceAmbient, showParticels;

	public PotionEffect(PotionEffectType type, int duration, int amplifier) {
		this(type, duration, amplifier, false, true);
	}
	
	public PotionEffect(PotionEffectType type, int duration, int amplifier, boolean reduceAmbient, boolean showParticles) {
		if (amplifier < 0 || amplifier > 127)
			amplifier = 0;
		this.amplifier = amplifier;
		this.type = type;
		this.reduceAmbient = reduceAmbient;
		this.showParticels = showParticles;
		this.duration = duration;
	}

	public PotionEffect clone() {
		try {
			return (PotionEffect) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public PotionEffectType getType() {
		return type;
	}

	public static PotionEffect createByPotionID(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasReducedAmbient() {
		return reduceAmbient;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public int getDuration() {
		return duration;
	}

	public boolean hasParticels() {
		return showParticels;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PotionEffect effect = (PotionEffect) obj;
		if (getAmplifier() != effect.getAmplifier())
			return false;
		if (getDuration() != effect.getDuration())
			return false;
		if (hasReducedAmbient() != effect.hasReducedAmbient())
			return false;
		if (hasParticels() != effect.hasParticels())
			return false;
		if (!getType().equals(effect.getType()))
			return false;
		return true;
	}

}
