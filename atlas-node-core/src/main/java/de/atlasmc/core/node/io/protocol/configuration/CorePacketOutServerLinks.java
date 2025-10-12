package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketServerLinks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundServerLinks;

public class CorePacketOutServerLinks extends CoreAbstractPacketServerLinks<ClientboundServerLinks> {

	@Override
	public ClientboundServerLinks createPacketData() {
		return new ClientboundServerLinks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundServerLinks.class);
	}

}
