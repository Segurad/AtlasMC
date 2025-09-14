package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketPing;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketInPong;

public class CorePacketInPong extends CoreAbstractPacketPing<PacketInPong> {

	@Override
	public PacketInPong createPacketData() {
		return new PacketInPong();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPong.class);
	}

}
