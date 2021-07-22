package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_EFFECT)
public interface PacketOutEntityEffect extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public int getEffectID();
	public int getAmplifier();
	public int getDuration();
	public boolean isAmbient();
	public boolean getShowParticles();
	public boolean getShowIcon();
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_EFFECT;
	}

}
