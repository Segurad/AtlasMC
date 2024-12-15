package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketPluginMessage;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketOutPluginMessage;

public class CorePacketOutPluginMessage extends CoreAbstractPacketPluginMessage<PacketOutPluginMessage> {

	@Override
	public PacketOutPluginMessage createPacketData() {
		return new PacketOutPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutPluginMessage.class);
	}

}
