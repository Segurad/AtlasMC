package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.potion.PotionEffect;
import de.atlasmc.potion.PotionEffectType;

@DefaultPacketID(PacketPlay.OUT_ENTITY_EFFECT)
public class PacketOutEntityEffect extends AbstractPacket implements PacketPlayOut {
	
	protected static final int
	FLAG_IS_AMBIENT = 0x01,
	FLAG_SHOW_PARTICLES = 0x02,
	FLAG_SHOW_ICON = 0x04;
	
	private int flags;
	private int entityID;
	private PotionEffectType effect;
	private int amplifier;
	private int duration;
	
	public int getEntityID() {
		return entityID;
	}

	public PotionEffectType getEffect() {
		return effect;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public int getDuration() {
		return duration;
	}

	public boolean isAmbient() {
		return (flags & FLAG_IS_AMBIENT) == FLAG_IS_AMBIENT;
	}
	
	public void setAmbient(boolean ambient) {
		this.flags |= FLAG_IS_AMBIENT;
	}

	public boolean getShowParticles() {
		return (flags & FLAG_SHOW_PARTICLES) == FLAG_SHOW_PARTICLES;
	}

	public boolean getShowIcon() {
		return (flags & FLAG_SHOW_ICON) == FLAG_SHOW_ICON;
	}

	public void setEntityID(int id) {
		this.entityID = id;
	}
	
	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setEffect(PotionEffectType effect) {
		this.effect = effect;
	}
	
	public int getFlags() {
		return flags;
	}
	
	/**
	 * Sets {@link PotionEffect} settings as int
	 * <ul>
	 * <li>0x01 - reduced ambient</li>
	 * <li>0x02 - show particles</li>
	 * <li>0x04 - show icon</li>
	 * </ul>
	 * @param flags
	 */
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
	public void setEffect(PotionEffect effect) {
		this.effect = effect.getType();
		amplifier = effect.getAmplifier();
		duration = effect.getDuration();
		flags = 0;
		if (effect.hasReducedAmbient())
			flags |= FLAG_IS_AMBIENT;
		if (effect.hasParticels())
			flags |= FLAG_SHOW_PARTICLES;
		if (effect.isShowingIcon())
			flags |= FLAG_SHOW_ICON;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_ENTITY_EFFECT;
	}

}
