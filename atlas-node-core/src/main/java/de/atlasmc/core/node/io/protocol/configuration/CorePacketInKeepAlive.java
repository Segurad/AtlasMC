package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketInKeepAlive;

public class CorePacketInKeepAlive extends CoreAbstractPacketKeepAlive<PacketInKeepAlive> {

	@Override
	public PacketInKeepAlive createPacketData() {
		return new PacketInKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInKeepAlive.class);
	}

}
