package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieRequest;

@DefaultPacketID(packetID = PacketPlay.OUT_COOKIE_REQUEST, definition = "cookie_request")
public class PacketOutCookieRequest extends AbstractPacketCookieRequest implements PacketPlayOut {

	@Override
	public int getDefaultID() {
		return OUT_COOKIE_REQUEST;
	}
	
}
