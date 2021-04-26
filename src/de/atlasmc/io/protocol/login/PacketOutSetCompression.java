package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketOutSetCompression extends Packet {
	
	public int getThreshold();
	
	@Override
	public default int getDefaultID() {
		return 0x03;
	}

}
