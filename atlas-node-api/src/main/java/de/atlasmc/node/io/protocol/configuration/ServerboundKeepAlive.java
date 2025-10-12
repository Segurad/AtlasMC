package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketConfiguration.IN_KEEP_ALIVE, definition = "keep_alive")
public class ServerboundKeepAlive extends AbstractPacketKeepAlive implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return IN_KEEP_ALIVE;
	}
	
}
