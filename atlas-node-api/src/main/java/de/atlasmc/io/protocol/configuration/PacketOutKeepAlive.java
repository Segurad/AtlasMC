package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketKeepAlive;

@DefaultPacketID(packetID = PacketConfiguration.OUT_KEEP_ALIVE, definition = "keep_alive")
public class PacketOutKeepAlive extends AbstractPacketKeepAlive implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_KEEP_ALIVE;
	}
	
}
