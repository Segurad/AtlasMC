package de.atlascore.io.protocol.play;

import de.atlascore.io.protocol.common.CoreAbstractPacketKeepAlive;
import de.atlasmc.io.Packet;
import de.atlasmc.io.protocol.play.PacketOutKeepAlive;

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
