package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPluginMessage;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketInPluginMessage;

public class CorePacketInPluginMessage extends CoreAbstractPacketPluginMessage<PacketInPluginMessage> {

	@Override
	public PacketInPluginMessage createPacketData() {
		return new PacketInPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPluginMessage.class);
	}

}
