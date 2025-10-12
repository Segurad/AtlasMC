package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketTransfer;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundTransfer;

public class CorePacketOutTransfer extends CoreAbstractPacketTransfer<ClientboundTransfer> {

	@Override
	public ClientboundTransfer createPacketData() {
		return new ClientboundTransfer();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundTransfer.class);
	}

}
