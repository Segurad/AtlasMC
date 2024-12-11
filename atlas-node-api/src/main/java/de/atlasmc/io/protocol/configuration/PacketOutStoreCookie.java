package de.atlasmc.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketConfiguration.OUT_STORE_COOKIE, definition = "store_cookie")
public class PacketOutStoreCookie extends AbstractPacketCookieData implements PacketConfigurationOut {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_STORE_COOKIE;
	}

}
