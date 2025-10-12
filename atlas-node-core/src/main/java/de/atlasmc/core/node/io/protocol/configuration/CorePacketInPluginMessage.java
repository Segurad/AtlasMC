package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPluginMessage;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ServerboundPluginMessage;

public class CorePacketInPluginMessage extends CoreAbstractPacketPluginMessage<ServerboundPluginMessage> {

	@Override
	public ServerboundPluginMessage createPacketData() {
		return new ServerboundPluginMessage();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundPluginMessage.class);
	}

}
