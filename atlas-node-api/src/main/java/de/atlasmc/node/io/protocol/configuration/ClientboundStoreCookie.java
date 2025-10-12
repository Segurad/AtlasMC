package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketConfiguration.OUT_STORE_COOKIE, definition = "store_cookie")
public class ClientboundStoreCookie extends AbstractPacketCookieData implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_STORE_COOKIE;
	}

}
