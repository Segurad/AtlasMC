package de.atlasmc.io.protocol.login;

import java.util.UUID;

import de.atlasmc.io.Packet;

public interface PacketOutLoginSuccess extends Packet {
	
	public UUID getUUID();
	public String getUsername();
	
	@Override
	public default int getDefaultID() {
		return 0x02;
	}

}
