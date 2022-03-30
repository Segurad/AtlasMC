package de.atlasmc.io.protocol.login;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_DISCONNECT)
public interface PacketOutDisconnect extends PacketLogin, PacketOutbound {
	
	public Chat getReason();
	
	@Override
	default int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
