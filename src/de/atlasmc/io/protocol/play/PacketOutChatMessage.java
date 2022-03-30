package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_CHAT_MESSAGE)
public interface PacketOutChatMessage extends PacketPlay, PacketOutbound {

	public Chat getMessage();
	
	public UUID getSender();
	
	public ChatType getType();
	
	public void setMessage(Chat chat);
	
	public void setSender(UUID sender);
	
	public void setType(ChatType type);
	
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
