package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketPlay.OUT_STORE_COOKIE, definition = "store_cookie")
public class PacketOutStoreCookie extends AbstractPacketCookieData implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_STORE_COOKIE;
	}

}
