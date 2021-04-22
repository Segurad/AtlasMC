package de.atlasmc.io.protocol.login;

import de.atlasmc.io.Packet;

public interface PacketInLoginStart extends Packet {
	
	public String getName();
	
	@Override
	default int getDefaultID() {
		return 0x00;
	}

}
