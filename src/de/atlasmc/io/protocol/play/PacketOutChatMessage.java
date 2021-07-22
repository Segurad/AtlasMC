package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CHAT_MESSAGE)
public interface PacketOutChatMessage extends PacketPlay, PacketOutbound {

	@Override
	default int getDefaultID() {
		return OUT_CHAT_MESSAGE;
	}
	
	public static enum ChatMessage {
		CHAT,
		SYSTEN,
		GANE_INFO
	}
	
}
