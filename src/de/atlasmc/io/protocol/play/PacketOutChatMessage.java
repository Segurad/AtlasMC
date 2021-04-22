package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutChatMessage extends Packet {

	@Override
	default int getDefaultID() {
		return 0x0E;
	}
	
	public static enum ChatMessage {
		CHAT,
		SYSTEN,
		GANE_INFO
	}
	
}
