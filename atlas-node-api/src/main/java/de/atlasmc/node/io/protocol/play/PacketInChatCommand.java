package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_CHAT_COMMAND, definition = "chat_command")
public class PacketInChatCommand extends AbstractPacket implements PacketPlayIn {

	public String command;
	
	@Override
	public int getDefaultID() {
		return IN_CHAT_COMMAND;
	}
	
}
