package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCookieRequest;

@DefaultPacketID(packetID = PacketConfiguration.OUT_COOKIE_REQUEST, definition = "cookie_request")
public class PacketOutCookieRequrest extends AbstractPacketCookieRequest implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_COOKIE_REQUEST;
	}

}
