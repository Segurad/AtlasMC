package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketUpdateTags;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundUpdateTags;

public class CorePacketOutUpdateTags extends CoreAbstractPacketUpdateTags<ClientboundUpdateTags> {

	@Override
	public ClientboundUpdateTags createPacketData() {
		return new ClientboundUpdateTags();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundUpdateTags.class);
	}

}
