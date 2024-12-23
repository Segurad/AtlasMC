package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketServerLinks;

@DefaultPacketID(packetID = PacketConfiguration.OUT_SERVER_LINKS, definition = "server_links")
public class PacketOutServerLinks extends AbstractPacketServerLinks implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_SERVER_LINKS;
	}

}
