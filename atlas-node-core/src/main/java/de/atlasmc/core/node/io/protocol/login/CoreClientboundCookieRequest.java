package de.atlasmc.core.node.io.protocol.login;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketCookieRequest;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.login.ClientboundCookieRequest;

public class CoreClientboundCookieRequest extends CoreAbstractPacketCookieRequest<ClientboundCookieRequest> {

	@Override
	public ClientboundCookieRequest createPacketData() {
		return new ClientboundCookieRequest();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundCookieRequest.class);
	}

}
