package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityEffect extends Packet {
	
	public int getEntityID();
	public int getEffectID();
	public int getAmplifier();
	public int getDuration();
	public boolean isAmbient();
	public boolean getShowParticles();
	public boolean getShowIcon();
	
	@Override
	public default int getDefaultID() {
		return 0x59;
	}

}
