package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_ACKNOWLEDGE_MESSAGE, definition = "chat_ack")
public class PacketInAcknowledgeMessage extends AbstractPacket implements PacketPlayIn {

	public int messageID;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_ACKNOWLEDGE_MESSAGE;
	}

}
