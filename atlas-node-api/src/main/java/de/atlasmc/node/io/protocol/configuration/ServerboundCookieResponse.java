package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketConfiguration.IN_COOKIE_RESPONSE, definition = "cookie_response")
public class ServerboundCookieResponse extends AbstractPacketCookieData implements PacketConfigurationServerbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.IN_COOKIE_RESPONSE;
	}
	
}
