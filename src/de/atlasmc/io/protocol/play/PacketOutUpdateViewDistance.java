package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUpdateViewDistance extends Packet {
	
	public int getDistance();
	
	@Override
	public default int getDefaultID() {
		return 0x41;
	}

}
