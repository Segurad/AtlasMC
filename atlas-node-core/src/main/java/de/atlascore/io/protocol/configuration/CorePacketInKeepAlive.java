package de.atlascore.io.protocol.configuration;

import de.atlascore.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.configuration.PacketInKeepAlive;

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
