package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketPing;

@DefaultPacketID(packetID = PacketConfiguration.OUT_PING, definition = "ping")
public class PacketOutPing extends AbstractPacketPing implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_PING;
	}
	
}
