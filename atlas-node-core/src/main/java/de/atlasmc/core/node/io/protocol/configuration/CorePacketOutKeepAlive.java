package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundKeepAlive;

public class CorePacketOutKeepAlive extends CoreAbstractPacketKeepAlive<ClientboundKeepAlive> {

	@Override
	public ClientboundKeepAlive createPacketData() {
		return new ClientboundKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundKeepAlive.class);
	}

}
