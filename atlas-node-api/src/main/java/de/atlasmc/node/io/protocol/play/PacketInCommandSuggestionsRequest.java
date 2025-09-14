package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_COMMAND_SUGGESTIONS_REQUEST, definition = "command_suggestion")
public class PacketInCommandSuggestionsRequest extends AbstractPacket implements PacketPlayIn {

	public int transactionID;
	public String text;
	
	@Override
	public int getDefaultID() {
		return IN_COMMAND_SUGGESTIONS_REQUEST;
	}
	
}
