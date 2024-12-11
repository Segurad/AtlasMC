package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPing;

@DefaultPacketID(packetID = PacketConfiguration.IN_PONG, definition = "pong")
public class PacketInPong extends AbstractPacketPing implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return IN_PONG;
	}
	
}
