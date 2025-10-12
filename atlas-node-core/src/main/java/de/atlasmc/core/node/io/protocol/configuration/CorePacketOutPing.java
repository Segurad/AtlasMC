package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPing;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundPing;

public class CorePacketOutPing extends CoreAbstractPacketPing<ClientboundPing> {

	@Override
	public ClientboundPing createPacketData() {
		return new ClientboundPing();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundPing.class);
	}

}
