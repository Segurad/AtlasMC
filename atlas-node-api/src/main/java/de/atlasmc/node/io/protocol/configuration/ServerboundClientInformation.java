package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketClientInformation;

@DefaultPacketID(packetID = PacketConfiguration.IN_CLIENT_INFORMATION, definition = "client_information")
public class ServerboundClientInformation extends AbstractPacketClientInformation implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return IN_CLIENT_INFORMATION;
	}
	
}
