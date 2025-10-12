package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKnownPacks;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundKnownPacks;

public class CorePacketOutKnownPacks extends CoreAbstractPacketKnownPacks<ClientboundKnownPacks> {

	@Override
	public ClientboundKnownPacks createPacketData() {
		return new ClientboundKnownPacks();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundKnownPacks.class);
	}

}
