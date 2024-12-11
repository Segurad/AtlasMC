package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketConfiguration.IN_FINISH_CONFIGURATION, definition = "finish_configuration")
public class PacketInFinishConfiguration extends AbstractPacket implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return IN_FINISH_CONFIGURATION;
	}
	
}
