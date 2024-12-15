package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketPluginMessage;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketInPluginMessage;

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
