package de.atlasmc.core.node.io.protocol.configuration;

import de.atlasmc.core.node.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.node.io.protocol.configuration.PacketOutKeepAlive;

public class CorePacketOutKeepAlive extends CoreAbstractPacketKeepAlive<PacketOutKeepAlive> {

	@Override
	public PacketOutKeepAlive createPacketData() {
		return new PacketOutKeepAlive();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutKeepAlive.class);
	}

}
