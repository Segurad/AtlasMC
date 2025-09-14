package de.atlasmc.node.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_SYSTEM_CHAT_MESSAGE, definition = "system_chat")
public class PacketOutSystemChatMessage extends AbstractPacket implements PacketPlayOut {

	public Chat message;
	public boolean actionbar;
	
	@Override
	public int getDefaultID() {
		return OUT_SYSTEM_CHAT_MESSAGE;
	}
	
}
