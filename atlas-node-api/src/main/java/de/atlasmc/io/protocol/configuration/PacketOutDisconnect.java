package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketDisconnect;

@DefaultPacketID(packetID = PacketConfiguration.OUT_DISCONNECT, definition = "disconnect")
public class PacketOutDisconnect extends AbstractPacketDisconnect implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_DISCONNECT;
	}
	
}
