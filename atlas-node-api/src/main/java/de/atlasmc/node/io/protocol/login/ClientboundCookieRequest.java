package de.atlasmc.node.io.protocol.login;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieRequest;

@DefaultPacketID(packetID = PacketLogin.OUT_COOKIE_REQUEST, definition = "cookie_request")
public class ClientboundCookieRequest extends AbstractPacketCookieRequest implements PacketLoginClientbound {

	@Override
	public int getDefaultID() {
		return PacketLogin.OUT_COOKIE_REQUEST;
	}
	
}
