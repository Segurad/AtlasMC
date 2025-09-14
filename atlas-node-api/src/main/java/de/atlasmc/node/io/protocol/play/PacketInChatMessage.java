package de.atlasmc.node.io.protocol.play;

import java.util.BitSet;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CHAT_MESSAGE, definition = "chat")
public class PacketInChatMessage extends AbstractPacket implements PacketPlayIn {

	public String message;
	public long messageTimestamp;
	public long salt;
	public byte[] signature;
	public int messageCount;
	public BitSet acknowledged;
	
	@Override
	public int getDefaultID() {
		return IN_CHAT_MESSAGE;
	}
	
}
