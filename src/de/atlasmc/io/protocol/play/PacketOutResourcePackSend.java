package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutResourcePackSend extends Packet {
	
	public String getURL();
	public String getHash();
	
	@Override
	default int getDefaultID() {
		return 0x38;
	}

}
