package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.OUT_FINISH_CONFIGURATION, definition = "finish_configuration")
public class PacketOutFinishConfiguration extends AbstractPacket implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return OUT_FINISH_CONFIGURATION;
	}
	
}
