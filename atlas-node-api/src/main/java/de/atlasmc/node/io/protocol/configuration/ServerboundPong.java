package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketPing;

@DefaultPacketID(packetID = PacketConfiguration.IN_PONG, definition = "pong")
public class ServerboundPong extends AbstractPacketPing implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return IN_PONG;
	}
	
}
