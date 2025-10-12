package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketStoreCookie;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundStoreCookie;

public class CorePacketOutStoreCookie extends CoreAbstractPacketStoreCookie<ClientboundStoreCookie> {

	@Override
	public ClientboundStoreCookie createPacketData() {
		return new ClientboundStoreCookie();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundStoreCookie.class);
	}

}
