package de.atlasmc.node.io.protocol.configuration;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketCookieRequest;

@DefaultPacketID(packetID = PacketConfiguration.OUT_COOKIE_REQUEST, definition = "cookie_request")
public class ClientboundCookieRequest extends AbstractPacketCookieRequest implements PacketConfigurationClientbound {
	
	@Override
	public int getDefaultID() {
		return PacketConfiguration.OUT_COOKIE_REQUEST;
	}

}
