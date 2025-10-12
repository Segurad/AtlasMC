package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ServerboundKeepAlive;

public class CorePacketInKeepAlive extends CoreAbstractPacketKeepAlive<ServerboundKeepAlive> {

	@Override
	public ServerboundKeepAlive createPacketData() {
		return new ServerboundKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundKeepAlive.class);
	}

}
