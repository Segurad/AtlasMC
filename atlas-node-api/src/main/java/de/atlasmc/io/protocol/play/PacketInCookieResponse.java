package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.protocol.common.AbstractPacketCookieData;

@DefaultPacketID(packetID = PacketPlay.IN_COOKIE_RESPONSE, definition = "cookie_response")
public class PacketInCookieResponse extends AbstractPacketCookieData implements PacketPlayIn {
	
	@Override
	public int getDefaultID() {
		return IN_COOKIE_RESPONSE;
	}

}
