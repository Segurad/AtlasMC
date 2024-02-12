package de.atlasmc.io.protocol.play;

import java.util.BitSet;
import java.util.List;
import java.util.UUID;

import de.atlasmc.chat.ChatSignature;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_CHAT_MESSAGE)
public class PacketOutChatMessage extends AbstractPacket implements PacketPlayOut {
	
	public UUID sender;
	public int index;
	public byte[] signature;
	
	public String message;
	public long messageTimestamp;
	public long salt;
	
	public List<ChatSignature> previous;
	
	public String unsignedContent;
	public int filterType;
	public BitSet filterBits;
	
	public int chatType;
	public String name;
	public String targetName;
	
	@Override
	public int getDefaultID() {
		return OUT_CHAT_MESSAGE;
	}

}
