package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CHAT_MESSAGE)
public interface PacketInChatMessage extends PacketPlay, PacketInbound {

	public String getMessage();
	
	@Override
	default int getDefaultID() {
		return IN_CHAT_MESSAGE;
	}
}
