package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutKeepAlive extends Packet {

	public long getKeepAlive();
	
	@Override
	public default int getDefaultID() {
		return 0x1F;
	}
	
}
