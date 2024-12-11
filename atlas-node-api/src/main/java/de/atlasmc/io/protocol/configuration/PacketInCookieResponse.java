package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketConfiguration.IN_COOKIE_RESPONSE, definition = "cookie_response")
public class PacketInCookieResponse extends AbstractPacketCookieData implements PacketConfigurationIn {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.IN_COOKIE_RESPONSE;
	}
	
}
