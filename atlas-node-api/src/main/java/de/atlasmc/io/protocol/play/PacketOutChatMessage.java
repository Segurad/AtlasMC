package de.atlasmc.io.protocol.play;

import java.util.BitSet;
import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatSignature;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_CHAT_MESSAGE, definition = "player_chat")
public class PacketOutChatMessage extends AbstractPacket implements PacketPlayOut {
	
	public UUID sender;
	public int index;
	public byte[] signature;
	
	public String message;
	public long messageTimestamp;
	public long salt;
	
	public List<ChatSignature> previous;
	
	public Chat unsignedContent;
	public int filterType;
	public BitSet filterBits;
	
	public int chatType;
	public Chat senderName;
	public Chat targetName;
	
	@Override
	public int getDefaultID() {
		return OUT_CHAT_MESSAGE;
	}

}
