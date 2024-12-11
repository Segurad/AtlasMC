package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_ACKNOWLEDGE_MESSAGE, definition = "acknowledge_message")
public class PacketInAcknowledgeMessage extends AbstractPacket implements PacketPlayIn {

	public int messageID;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_ACKNOWLEDGE_MESSAGE;
	}

}
