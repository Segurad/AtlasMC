package de.atlasmc.io.protocol.login;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketLogin.OUT_DISCONNECT)
public interface PacketOutDisconnect extends PacketLogin, PacketOutbound {
	
	public ChatComponent getReason();
	
	@Override
	default int getDefaultID() {
		return OUT_DISCONNECT;
	}

}
