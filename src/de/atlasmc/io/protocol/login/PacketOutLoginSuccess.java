package de.atlasmc.io.protocol.login;

import java.util.UUID;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_LOGIN_SUCCESS)
public interface PacketOutLoginSuccess extends PacketLogin, PacketOutbound {
	
	public UUID getUUID();
	public String getUsername();
	
	@Override
	public default int getDefaultID() {
		return OUT_LOGIN_SUCCESS;
	}

}
