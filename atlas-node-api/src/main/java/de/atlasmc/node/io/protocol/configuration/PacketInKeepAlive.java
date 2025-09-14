package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketConfiguration.IN_KEEP_ALIVE, definition = "keep_alive")
public class PacketInKeepAlive extends AbstractPacketKeepAlive implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
