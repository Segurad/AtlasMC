package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieRequest;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundCookieRequest;

public class CorePacketOutCookieRequest extends CoreAbstractPacketCookieRequest<ClientboundCookieRequest> {

	@Override
	public ClientboundCookieRequest createPacketData() {
		return new ClientboundCookieRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundCookieRequest.class);
	}

}
