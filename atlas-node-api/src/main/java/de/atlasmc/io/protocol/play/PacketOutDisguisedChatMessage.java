package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_DISGUISED_CHAT_MESSAGE, definition = "disguised_chat")
public class PacketOutDisguisedChatMessage extends AbstractPacket implements PacketPlayOut {

	public Chat message;
	public int chatType;
	public Chat sender;
	public Chat target;
	
	@Override
	public int getDefaultID() {
		return OUT_DISGUISED_CHAT_MESSAGE;
	}
	
}
