package de.atlasmc.io.protocol.status;

import de.atlasmc.io.Packet;

public interface PacketOutResponse extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x00;
	}

}
