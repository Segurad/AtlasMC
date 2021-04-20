package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutChatMessage extends Packet {

	public static enum ChatMessage {
		CHAT,
		SYSTEN,
		GANE_INFO
	}
	
}
