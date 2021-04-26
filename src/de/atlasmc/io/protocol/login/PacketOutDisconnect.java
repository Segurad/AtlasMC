package de.atlasmc.io.protocol.login;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.Packet;

public interface PacketOutDisconnect extends Packet {
	
	public ChatComponent getReason();
	
	@Override
	default int getDefaultID() {
		return 0x00;
	}

}
