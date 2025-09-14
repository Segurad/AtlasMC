package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.node.potion.PotionEffectType;

@DefaultPacketID(packetID = PacketPlay.OUT_ENTITY_EFFECT, definition = "update_mob_effect")
public class PacketOutEntityEffect extends AbstractPacket implements PacketPlayOut {
	
	public static final int
	FLAG_IS_AMBIENT = 0x01,
	FLAG_SHOW_PARTICLES = 0x02,
	FLAG_SHOW_ICON = 0x04;
	
	/**
	 * Sets {@link PotionEffect} settings as int
	 * <ul>
	 * <li>0x01 - reduced ambient</li>
	 * <li>0x02 - show particles</li>
	 * <li>0x04 - show icon</li>
	 * </ul>
	 */
	public int flags;
	public int entityID;
	public PotionEffectType effect;
	public int amplifier;
	public int duration;

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
