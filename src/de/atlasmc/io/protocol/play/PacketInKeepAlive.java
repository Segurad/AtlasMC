package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketInKeepAlive extends Packet {
	
	public long getKeepAliveID();

	@Override
	default int getDefaultID() {
		return 0x10;
	}
	
}
