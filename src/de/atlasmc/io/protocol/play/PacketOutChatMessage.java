package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CHAT_MESSAGE)
public class PacketOutChatMessage extends AbstractPacket implements PacketPlayOut {

	private UUID sender;
	private String message;
	private ChatType type;
	
	public UUID getSender() {
		return sender;
	}
	
	public void setSender(UUID sender) {
		this.sender = sender;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setMessage(Chat message) {
		this.message = message.getText();
	}
	
	public ChatType getType() {
		return type;
	}

	public void setType(ChatType type) {
		this.type = type;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_CHAT_MESSAGE;
	}
	
}
