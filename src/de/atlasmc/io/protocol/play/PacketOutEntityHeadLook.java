package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutEntityHeadLook extends Packet {
	
	public int getEntityID();
	public float getYaw();
	
	@Override
	public default int getDefaultID() {
		return 0x3A;
	}

}
