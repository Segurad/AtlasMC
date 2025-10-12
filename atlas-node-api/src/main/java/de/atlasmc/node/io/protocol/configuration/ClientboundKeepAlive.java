package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketConfiguration.OUT_KEEP_ALIVE, definition = "keep_alive")
public class ClientboundKeepAlive extends AbstractPacketKeepAlive implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
