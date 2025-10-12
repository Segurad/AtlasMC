package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPing;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.ServerboundPong;

public class CorePacketInPong extends CoreAbstractPacketPing<ServerboundPong> {

	@Override
	public ServerboundPong createPacketData() {
		return new ServerboundPong();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundPong.class);
	}

}
