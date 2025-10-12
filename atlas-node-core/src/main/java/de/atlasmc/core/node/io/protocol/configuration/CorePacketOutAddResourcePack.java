package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketAddResourcePack;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundAddResourcePack;

public class CorePacketOutAddResourcePack extends CoreAbstractPacketAddResourcePack<ClientboundAddResourcePack> {

	@Override
	public ClientboundAddResourcePack createPacketData() {
		return new ClientboundAddResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundAddResourcePack.class);
	}

}
