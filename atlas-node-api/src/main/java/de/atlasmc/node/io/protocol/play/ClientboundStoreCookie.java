package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketPlay.OUT_STORE_COOKIE, definition = "store_cookie")
public class ClientboundStoreCookie extends AbstractPacketCookieData implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_STORE_COOKIE;
	}

}
