package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPluginMessage;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ClientboundPluginMessage;

public class CorePacketOutPluginMessage extends CoreAbstractPacketPluginMessage<ClientboundPluginMessage> {

	@Override
	public ClientboundPluginMessage createPacketData() {
		return new ClientboundPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ClientboundPluginMessage.class);
	}

}
