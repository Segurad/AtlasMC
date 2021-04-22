package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketOutDisconnect extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x00;
	}

}
