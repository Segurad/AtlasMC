package de.atlasmc.core.node.io.protocol.login;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketText;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.login.ClientboundDisconnect;

public class CoreClientboundDisconnect extends CoreAbstractPacketText<ClientboundDisconnect> {

	@Override
	public ClientboundDisconnect createPacketData() {
		return new ClientboundDisconnect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundDisconnect.class);
	}

}
