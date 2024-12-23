package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.IN_ACKNOWLEDGE_CONFIGURATION, definition = "configuration_acknowledged")
public class PacketInAcknowledgeConfiguration extends AbstractPacket implements PacketPlayIn {
	
	@Override
	public int getDefaultID() {
		return IN_ACKNOWLEDGE_CONFIGURATION;
	}

}
