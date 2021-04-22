package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutUpdateLight extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x23;
	}

}
