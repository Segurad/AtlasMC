package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutCamera extends Packet {
	
	public int getEntityID();
	
	@Override
	public default int getDefaultID() {
		return 0x3E;
	}

}
