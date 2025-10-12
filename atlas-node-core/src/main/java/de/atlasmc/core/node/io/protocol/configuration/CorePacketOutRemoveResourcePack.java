package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketRemoveResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundRemoveResourcePack;

public class CorePacketOutRemoveResourcePack extends CoreAbstractPacketRemoveResourcePack<ClientboundRemoveResourcePack> {

	@Override
	public ClientboundRemoveResourcePack createPacketData() {
		return new ClientboundRemoveResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundRemoveResourcePack.class);
	}

}
