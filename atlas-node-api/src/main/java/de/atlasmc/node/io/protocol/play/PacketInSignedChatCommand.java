package de.atlasmc.node.io.protocol.play;

import java.util.BitSet;
import java.util.Map;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_SIGNED_CHAT_COMMAND, definition = "chat_command_signed")
public class PacketInSignedChatCommand extends AbstractPacket implements PacketPlayIn {

	public String command;
	public long commandTimestamp;
	public long salt;
	public Map<String, byte[]> arguments;
	public int messageCount;
	public BitSet acknowledge;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_SIGNED_CHAT_COMMAND;
	}

}
