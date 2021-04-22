package de.atlasmc.io.protocol.status;

import de.atlasmc.io.Packet;

public interface PacketInPing extends Packet {
	
	public long getPing();
	
	@Override
	default int getDefaultID() {
		return 0x01;
	}

}
