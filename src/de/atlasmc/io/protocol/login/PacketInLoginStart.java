package de.atlasmc.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketLogin.IN_LOGIN_START)
public interface PacketInLoginStart extends PacketLogin, PacketInbound {
	
	public String getName();
	
	@Override
	default int getDefaultID() {
		return IN_LOGIN_START;
	}

}
