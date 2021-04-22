package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutDisconnect extends Packet {
	
	public String getReason();
	
	@Override
	default int getDefaultID() {
		return 0x19;
	}

}
